package com.docker.demo.services

import com.docker.demo.entities.ShopWork
import com.docker.demo.repositories.shopwork.ShopWorkRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

import static java.net.URI.create
import static org.springframework.web.reactive.function.server.ServerResponse.*

@Service
class ShopWorkService {

    ShopWorkRepository repository

    ShopWorkService(ShopWorkRepository repository) {
        this.repository = repository
    }

    def getOne(ServerRequest request) {
        ok().body(repository.findById(request.pathVariable('id')), ShopWork).log()
    }

    def findAllShopWork() {
        ok().body(repository.findAll(), ShopWork).log()
    }

    def removeShopWork(ServerRequest request) {
        request.bodyToMono(ShopWork).flatMap{ shopWork ->
            accepted()
                    .body(repository.deleteById(shopWork.id), Void)
                    .log()
        }
    }

    def addShopWork(ServerRequest request) {
        request.bodyToMono(ShopWork).flatMap{ shopWork ->
            created(create("/shopwork/" + shopWork.id))
                    .body(repository.insert(shopWork), ShopWork)
                    .log()
        }
    }

    Mono<ServerResponse> removeAll() {
        ok().body(repository.deleteAll(), Void)
    }

    def updateShopWork(ServerRequest request) {
        request.bodyToMono(ShopWork)
                .flatMap{update ->
                    repository.findById(update.id)
                            .flatMap{original ->
                                updateShopWork(original, update)

                                accepted()
                                        .body(repository.save(original), ShopWork)
                                        .log()
                            }
                }
    }

    static ShopWork updateShopWork(ShopWork original, ShopWork update) {
        original.sku = update.sku ?: original.sku
        original.name = update.name ?: original.name
        original.price = update.price ?: original.price
        original.employeePrice = update.employeePrice ?: original.employeePrice
        original
    }
}
