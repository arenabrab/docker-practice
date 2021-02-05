package com.docker.demo.repositories.products

import com.docker.demo.entities.productcategories.Gem
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface GemRepository extends ReactiveMongoRepository<Gem, String>{
    Mono<Gem>findByImage(objectId)
}
