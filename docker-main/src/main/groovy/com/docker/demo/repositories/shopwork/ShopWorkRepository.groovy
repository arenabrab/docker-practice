package com.docker.demo.repositories.shopwork

import com.docker.demo.entities.ShopWork
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ShopWorkRepository extends ReactiveMongoRepository<ShopWork, String> {
}
