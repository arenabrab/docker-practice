package com.docker.demo.services

import com.docker.demo.entities.Image
import com.docker.demo.entities.Product
import org.bson.types.Binary
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.codec.multipart.Part
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

import static reactor.core.publisher.Mono.just

@Service
class ProductService {
    ReactiveGridFsTemplate gridFsTemplate

    ProductService(ReactiveGridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate
    }

    static Product update(Product original, Product update) {
        // TODO - rework for json or image update
        original.sku = update.sku ?: original.sku
        original.name = update.name ?: original.name
        original.price = update.price ?: original.price
        original.employeePrice = update.employeePrice ?: original.employeePrice
        original.pageViews = update.pageViews ?: original.pageViews
        original.productMaterial = update.productMaterial ?: original.productMaterial
        original
    }

    Mono<Product> addImage(Product product) {
        gridFsTemplate.findOne(new Query(Criteria.where("_id").is(product.image.objectId)))
                .flatMap{file -> gridFsTemplate.getResource(file)}
                .flatMap{reactiveResource -> reactiveResource.getInputStream()}
                .flatMap{inputStream -> just(inputStream.getBytes())}
                .flatMap{bytes -> just(new Binary(bytes))}
                .flatMap{binary -> just(product.tap {it.image.tap {it.image = binary}})}
    }

    Mono<Product> enrichProduct(Product product) {
        Optional.ofNullable(product)
                .map{it.image}
                .map{addImage(product) as Mono<Product>}
                .orElseGet{just(createBlankImage(product))}
    }

    Mono<ObjectId> storeImage(Part part)  {
        String name = Optional.ofNullable(part)
                .map{ (FilePart) it }
                .map{ it.filename() }
                .orElseGet{-> "No Picture attached"}

        Optional.ofNullable(part)
                .map{ it.content() }
                .map{ gridFsTemplate.store(it, name) }
                .orElseGet{ Mono.&empty }
    }

    static Product createBlankImage(Product product) {
        product.tap {it.setImage(new Image(image: new Binary("".getBytes())))}
    }
}
