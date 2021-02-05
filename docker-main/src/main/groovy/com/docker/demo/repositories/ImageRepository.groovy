package com.docker.demo.repositories

import com.docker.demo.entities.Image
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository extends ReactiveMongoRepository<Image, String> {
}