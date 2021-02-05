package com.docker.demo.entities

import groovy.transform.Canonical
import org.bson.types.Binary
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Canonical
@Document(collection = "images")
class Image {
    @Id
    @Indexed
    String name
    Binary image
    ObjectId objectId
}
