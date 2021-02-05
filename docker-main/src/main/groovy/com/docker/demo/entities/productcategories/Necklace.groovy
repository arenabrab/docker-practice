package com.docker.demo.entities.productcategories

import com.docker.demo.entities.Product
import com.docker.demo.enums.ClaspType
import groovy.transform.Canonical
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Canonical
@TypeAlias("necklace")
@Document(collection = "necklaces")
class Necklace extends Product {

    enum ChainType {
        CABLE, CURB, ROPE, ANCHOR, BALL
    }
    enum ChainLength {
        CHOKER, PRINCESS, MATINEE, OPERA, ROPE
    }

    ClaspType claspType
    ChainType chainType
    ChainLength chainLength
}



