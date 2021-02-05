package com.docker.demo.entities

import com.docker.demo.enums.ProductMaterial
import groovy.transform.Canonical
import org.springframework.data.mongodb.core.mapping.Document

@Canonical
@Document
abstract class Product extends SalesUnit {
    List<ProductMaterial> productMaterial
    int pageViews
    Image image
}
