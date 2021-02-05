package com.docker.demo.repositories

import com.docker.demo.entities.orders.Order
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository extends ReactiveMongoRepository<Order, String> {

}