package com.docker.demo.repositories.products

import com.docker.demo.entities.productcategories.Earring
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EarringRepository extends ReactiveMongoRepository<Earring, String>{
}
