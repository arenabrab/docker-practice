package com.docker.demo.services.products

import com.docker.demo.entities.Image
import com.docker.demo.entities.Product
import com.docker.demo.entities.productcategories.Necklace
import com.docker.demo.pogos.SortOrder
import com.docker.demo.repositories.products.NecklaceRepository
import com.docker.demo.services.ProductService
import org.bson.types.ObjectId
import org.springframework.core.ResolvableType
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.multipart.Part
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import static java.net.URI.create
import static org.springframework.data.domain.Sort.Direction.valueOf
import static org.springframework.data.domain.Sort.by
import static org.springframework.web.reactive.function.server.ServerResponse.*
import static reactor.core.publisher.Mono.deferWithContext
import static reactor.core.publisher.Mono.just
import static reactor.core.publisher.Mono.zip

@Service
class NecklaceService extends ProductService {
    NecklaceRepository repository

    NecklaceService(ReactiveGridFsTemplate gridFsTemplate, NecklaceRepository repository) {
        super(gridFsTemplate)
        this.repository = repository
    }

    Mono<ServerResponse> getOne(ServerRequest request) {
        ok().body(repository.findById(request.pathVariable('id')), Necklace)
    }

    Mono<ServerResponse> getAll(ServerRequest request) {
        ok().body(repository.findAll(), Necklace)
    }

    Mono<ServerResponse> addOne(ServerRequest request) {
        request.multipartData()
                .flatMap { data ->
                    Map<String, Part> parts = data.toSingleValueMap()

                    Mono<Necklace> necklace = getNecklace(parts.get('json'))

                    Mono<Necklace> output = parts.get('image') ? zipNecklace(necklace, storeImage(parts.get('image'))) : necklace
                    output.flatMap { created(create("/necklaces/" + it.id)).body(repository.insert(it), Necklace) }
                }
    }

    Mono<ServerResponse> getSortedNecklaces(ServerRequest request) {
        Flux<Necklace> necklaces = request.bodyToFlux(SortOrder)
                .flatMap{ repository.findAll(by(valueOf(it.direction), it.property))}
                .flatMapSequential{enrichProduct(it)} as Flux<Necklace>

        ok().body(necklaces, Necklace)

    }

    Mono<ServerResponse> update(ServerRequest request) {
        // TODO - rework for json or image update
        request.bodyToMono(Necklace)
                .flatMap{repository.findById(it.id)
                        .flatMap{original ->
                            updateNecklace(original, it)

                            accepted().body(repository.save(original), Necklace)}}
    }

    Mono<ServerResponse> removeOne(ServerRequest request) {
        request.bodyToMono(Necklace).flatMap {
            accepted().body(repository.deleteById(it.id), Void)
        }
    }

    Mono<ServerResponse> removeAll() {
        ok().body(repository.deleteAll(), Void)
    }

    static Mono<Necklace> zipNecklace(Mono<Necklace> necklace, Mono<ObjectId> objectId) {
        zip(necklace, objectId)
                .flatMap { tuple ->
                    Optional.ofNullable(tuple.t2).ifPresent {
                        tuple.t1.setImage(new Image(objectId: it))
                    }
                    just(tuple.t1)}
    }

    static Mono<Necklace> getNecklace(Part part) {
        part.content()
                .next()
                .flatMap{  new Jackson2JsonDecoder()
                        .decodeToMono(just(it), ResolvableType.forClass(Necklace), null, null)
                } as Mono<Necklace>
    }

    static Necklace updateNecklace(Necklace original, Necklace update) {
        ProductService.update(original, update)
        original.claspType = update.claspType ?: original.claspType
        original.chainLength = update.chainLength ?: original.chainLength
        original.chainType = update.chainType ?: original.chainType
        original
    }
}
