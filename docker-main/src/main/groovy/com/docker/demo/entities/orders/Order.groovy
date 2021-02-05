package com.docker.demo.entities.orders

import groovy.transform.Canonical
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@Canonical
class Order {
    @Id
    String id
    List<LineItem> lineItems
    BigDecimal totalPrice
}
