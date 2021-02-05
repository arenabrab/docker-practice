package com.docker.demo.entities

import groovy.transform.Canonical
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Canonical
@Document
abstract class SalesUnit {
    @Id
    String id
    @Indexed
    String name
    int sku
    BigDecimal price
    BigDecimal employeePrice
}
