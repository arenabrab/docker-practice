package com.docker.demo.entities.productcategories

import com.docker.demo.entities.Product
import groovy.transform.Canonical
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Canonical
@TypeAlias("earring")
@Document(collection = "earrings")
class Earring extends Product {
    enum Style {
        DANGLES, HOOPS, STUDS
    }

    Style style
}
