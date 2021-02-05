package com.docker.demo.entities.productcategories

import com.docker.demo.entities.Product
import com.docker.demo.pogos.GemSpecs
import groovy.transform.Canonical
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Canonical
@TypeAlias("gem")
@Document(collection = "gems")
class Gem extends Product {

    enum GemShape {
        ROUND, PRINCESS, OVAL, CUSHION, PEAR, EMERALD, RADIANT, MARQUISE, HEART, ASSCHER
    }

    GemShape cutType
    double caratWeight
    GemSpecs gemSpecs
}
