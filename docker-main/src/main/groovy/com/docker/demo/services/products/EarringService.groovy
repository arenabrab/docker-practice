package com.docker.demo.services.products

import com.docker.demo.entities.Image
import com.docker.demo.entities.productcategories.Earring
import com.docker.demo.pogos.SortOrder
import com.docker.demo.repositories.products.EarringRepository
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
import static reactor.core.publisher.Mono.just
import static reactor.core.publisher.Mono.zip

@Service
class EarringService extends ProductService {
    EarringRepository repository

    EarringService(ReactiveGridFsTemplate gridFsTemplate, EarringRepository repository) {
        super(gridFsTemplate)
        this.repository = repository
    }

    Mono<ServerResponse> getOne(ServerRequest request) {
        ok().body(repository.findById(request.pathVariable('id')), Earring)
    }

    Mono<ServerResponse> getAll(ServerRequest request) {
        ok().body(repository.findAll(), Earring)
    }

    Mono<ServerResponse> addOne(ServerRequest request) {
        request.multipartData()
                .flatMap { data ->
                    Map<String, Part> parts = data.toSingleValueMap()

                    Mono<Earring> earring = getEarring(parts.get('json'))

                    Mono<Earring> output = parts.get('image') ? zipEarring(earring, storeImage(parts.get('image'))) : earring
                    output.flatMap { created(create("/earrings/" + it.id)).body(repository.insert(it), Earring) }
                }
    }

    Mono<ServerResponse> getSortedEarrings(ServerRequest request) {
        Flux<Earring> earrings = request.bodyToFlux(SortOrder)
                .flatMap{ repository.findAll(by(valueOf(it.direction), it.property))}
                .flatMapSequential{enrichProduct(it)} as Flux<Earring>

        ok().body(earrings, Earring)

    }

    Mono<ServerResponse> update(ServerRequest request) {
        // TODO - rework for json or image update
        request.bodyToMono(Earring)
                .flatMap{repository.findById(it.id)
                        .flatMap{original ->
                            updateEarring(original, it)

                            accepted().body(repository.save(original), Earring)}}
    }

    Mono<ServerResponse> removeOne(ServerRequest request) {
        request.bodyToMono(Earring).flatMap {
            accepted().body(repository.deleteById(it.id), Void)
        }
    }

    Mono<ServerResponse> removeAll() {
        ok().body(repository.deleteAll(), Void)
    }

    static Mono<Earring> zipEarring(Mono<Earring> earring, Mono<ObjectId> objectId) {
        zip(earring, objectId)
                .flatMap { tuple ->
                    Optional.ofNullable(tuple.t2).ifPresent {
                        tuple.t1.setImage(new Image(objectId: it))
                    }
                    just(tuple.t1)}
    }

    static Mono<Earring> getEarring(Part part) {
        part.content()
                .next()
                .flatMap{  new Jackson2JsonDecoder()
                        .decodeToMono(just(it), ResolvableType.forClass(Earring), null, null)
                } as Mono<Earring>
    }

    static Earring updateEarring(Earring original, Earring update) {
        ProductService.update(original, update)
        original.style = update.style ?: original.style
        original
    }
}
