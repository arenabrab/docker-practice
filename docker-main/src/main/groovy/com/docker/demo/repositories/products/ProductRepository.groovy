package com.docker.demo.repositories.products

import com.docker.demo.entities.Product
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
