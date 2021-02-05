package com.docker.demo.services.products

import com.docker.demo.entities.Image
import com.docker.demo.entities.productcategories.Gem
import com.docker.demo.pogos.SortOrder
import com.docker.demo.repositories.products.GemRepository
import com.docker.demo.services.ProductService
import groovy.util.logging.Slf4j
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
@Slf4j
class GemsService extends ProductService {
    GemRepository repository

    GemsService(ReactiveGridFsTemplate gridFsTemplate, GemRepository repository) {
        super(gridFsTemplate)
        this.repository = repository
    }

    Mono<ServerResponse> getOne(ServerRequest request) {
        ok().body(repository.findById(request.pathVariable('id')), Gem)
    }

    Mono<ServerResponse> getAll(ServerRequest request) {
        ok().body(repository.findAll(), Gem)
    }

    Mono<ServerResponse> addOne(ServerRequest request) {
        request.multipartData()
                .flatMap { data ->
                    Map<String, Part> parts = data.toSingleValueMap()

                    Mono<Gem> gem = getGem(parts.get('json'))

                    Mono<Gem> output = parts.get('image') ? zipGem(gem, storeImage(parts.get('image'))) : gem
                    output.flatMap { created(create("/gems/" + it.id)).body(repository.insert(it), Gem) }
                }
    }

    Mono<ServerResponse> getSortedGems(ServerRequest request) {
        Flux<Gem> gems = request.bodyToFlux(SortOrder)
                .flatMap{ repository.findAll(by(valueOf(it.direction), it.property))}
                .flatMapSequential{enrichProduct(it)} as Flux<Gem>

        ok().body(gems, Gem)

    }

    Mono<ServerResponse> update(ServerRequest request) {
        // TODO - rework for json or image update
        request.bodyToMono(Gem)
                .flatMap{repository.findById(it.id)
                            .flatMap{original ->
                                updateGem(original, it)

                                accepted().body(repository.save(original), Gem)}}
    }

    Mono<ServerResponse> removeOne(ServerRequest request) {
        request.bodyToMono(Gem).flatMap {
            accepted().body(repository.deleteById(it.id), Void)
        }
    }

    Mono<ServerResponse> removeAll() {
        ok().body(repository.deleteAll(), Void)
    }

    static Mono<Gem> zipGem(Mono<Gem> gem, Mono<ObjectId> objectId) {
        zip(gem, objectId)
                .flatMap { tuple ->
                    Optional.ofNullable(tuple.t2).ifPresent {
                        tuple.t1.setImage(new Image(objectId: it))
                    }
                    just(tuple.t1)}
    }

    static Mono<Gem> getGem(Part part) {
        part.content()
                .next()
                .flatMap{  new Jackson2JsonDecoder()
                        .decodeToMono(just(it), ResolvableType.forClass(Gem), null, null)
                } as Mono<Gem>
    }

    static Gem updateGem(Gem original, Gem update) {
        ProductService.update(original, update)
        original.gemSpecs.certification = update.gemSpecs.certification ?: original.gemSpecs.certification
        original.gemSpecs.clarity = update.gemSpecs.clarity ?: original.gemSpecs.clarity
        original.gemSpecs.color = update.gemSpecs.color ?: original.gemSpecs.color
        original.gemSpecs.cut = update.gemSpecs.cut ?: original.gemSpecs.cut
        original.caratWeight = update.caratWeight ?: original.caratWeight
        original.cutType = update.cutType ?: original.cutType
        original
    }
}