package com.docker.client.entities

import groovy.transform.Canonical

@Canonical
class Product {
    String id
    String name
    BigDecimal price
    int pageViews
}