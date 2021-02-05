package com.docker.demo.entities.orders

import com.docker.demo.entities.Product
import groovy.transform.Canonical
import org.springframework.data.mongodb.core.mapping.Document

@Canonical
@Document
abstract class LineItem {
    Product product
    BigDecimal price
}
