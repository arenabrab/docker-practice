package com.docker.demo.services

import com.docker.demo.entities.orders.Order
import com.docker.demo.repositories.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

import static java.net.URI.*
import static org.springframework.web.reactive.function.server.ServerResponse.accepted
import static org.springframework.web.reactive.function.server.ServerResponse.created
import static org.springframework.web.reactive.function.server.ServerResponse.ok

@Service
class OrderService {

    OrderRepository repository

    OrderService(OrderRepository repository) {
        this.repository = repository
    }

    Mono<ServerResponse> getOne(ServerRequest request) {
        ok().body(repository.findById(request.pathVariable('id')), Order)
    }

    Mono<ServerResponse> getAll(ServerRequest request) {
        ok().body(repository.findAll(), Order)
    }

    Mono<ServerResponse> addOne(ServerRequest request) {
        request.bodyToMono(Order).flatMap{ created(create("/orders/" + it.id)).body(repository.save(it), Order) }
    }

    Mono<ServerResponse> removeOne(ServerRequest request) {
        request.bodyToMono(Order).flatMap {
            accepted().body(repository.deleteById(it.id), Void)
        }
    }

    Mono<ServerResponse> removeAll() {
        ok().body(repository.deleteAll(), Void)
    }
}
