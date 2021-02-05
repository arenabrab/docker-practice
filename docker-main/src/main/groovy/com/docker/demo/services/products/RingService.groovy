package com.docker.demo.services.products

import com.docker.demo.entities.Image
import com.docker.demo.entities.productcategories.Ring
import com.docker.demo.pogos.SortOrder
import com.docker.demo.repositories.products.RingRepository
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
class RingService extends ProductService {
    RingRepository repository

    RingService(ReactiveGridFsTemplate gridFsTemplate, RingRepository repository) {
        super(gridFsTemplate)
        this.repository = repository
    }

    Mono<ServerResponse> getOne(ServerRequest request) {
        ok().body(repository.findById(request.pathVariable('id')), Ring)
    }

    Mono<ServerResponse> getAll(ServerRequest request) {
        ok().body(repository.findAll(), Ring)
    }

    Mono<ServerResponse> addOne(ServerRequest request) {
        request.multipartData()
                .flatMap { data ->
                    Map<String, Part> parts = data.toSingleValueMap()
                    log.info("=================> parts: " + parts.size())
                    Mono<Ring> ring = getRing(parts.get('json'))

                    Mono<Ring> output = parts.get('image') ? zipRing(ring, storeImage(parts.get('image'))) : ring
                    output.flatMap { created(create("/rings/" + it.id)).body(repository.insert(it), Ring) }
                }
    }

    Mono<ServerResponse> getSortedRings(ServerRequest request) {
        Flux<Ring> rings = request.bodyToFlux(SortOrder)
                .flatMap{ repository.findAll(by(valueOf(it.direction), it.property))}
                .flatMapSequential{enrichProduct(it)} as Flux<Ring>

        ok().body(rings, Ring)

    }

    Mono<ServerResponse> update(ServerRequest request) {
        request.bodyToMono(Ring)
                .flatMap{update ->
                    repository.findById(update.id)
                            .flatMap{original ->
                                updateRing(original, update)

                                accepted()
                                        .body(repository.save(original), Ring)
                            }
                }
    }

    Mono<ServerResponse> removeOne(ServerRequest request) {
        request.bodyToMono(Ring).flatMap{
            accepted().body(repository.deleteById(it.id), Void)
        }
    }

    Mono<ServerResponse> removeAll() {
        ok().body(repository.deleteAll(), Void)
    }

    static Mono<Ring> zipRing(Mono<Ring> ring, Mono<ObjectId> objectId) {
        zip(ring, objectId)
                .flatMap { tuple ->
                    Optional.ofNullable(tuple.t2)
                            .ifPresent {
                                tuple.t1.setImage(new Image(objectId: it))}
                    just(tuple.t1)}
    }

    static Mono<Ring> getRing(Part part) {
        System.out.println("---------->" + part)
        part.content()
                .next()
                .flatMap{  new Jackson2JsonDecoder()
                        .decodeToMono(just(it), ResolvableType.forClass(Ring), null, null)
                } as Mono<Ring>
    }

    static Ring updateRing(Ring original, Ring update) {
        ProductService.update(original, update)
        original.bandWidth = update.bandWidth ?: original.bandWidth
        original.numberOfStones = update.numberOfStones ?: original.numberOfStones
        original.ringSizeSmallest = update.ringSizeSmallest ?: original.ringSizeSmallest
        original.ringSizeLargest = update.ringSizeLargest ?: original.ringSizeLargest
        original
    }

    static Range<Double> getRingSizeRange(double low, double high) {
        (low..high)*.div(4)
    }
}
