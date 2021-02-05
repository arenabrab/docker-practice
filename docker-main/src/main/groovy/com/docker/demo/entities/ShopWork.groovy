package com.docker.demo.entities

import groovy.transform.Canonical
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Canonical
@TypeAlias("shopWork")
@Document(collection = "shopWork")
abstract class ShopWork extends SalesUnit {
}
