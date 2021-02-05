package com.docker.demo.entities.productcategories

import com.docker.demo.entities.Product
import groovy.transform.Canonical
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Canonical
@TypeAlias("ring")
@Document(collection = "rings")
class Ring extends Product {
    double bandWidth
    int numberOfStones
    int ringSizeSmallest, ringSizeLargest
}
