package com.docker.demo.repositories.products


import com.docker.demo.entities.productcategories.Ring
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RingRepository extends ReactiveMongoRepository<Ring, String> {
}
