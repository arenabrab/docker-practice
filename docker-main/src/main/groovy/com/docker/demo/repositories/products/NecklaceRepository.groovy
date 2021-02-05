package com.docker.demo.repositories.products

import com.docker.demo.entities.productcategories.Necklace
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface NecklaceRepository extends ReactiveMongoRepository<Necklace, String> {
}