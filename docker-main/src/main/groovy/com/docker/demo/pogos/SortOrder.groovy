package com.docker.demo.pogos

import groovy.transform.Canonical

@Canonical
class SortOrder {
    String direction
    String[] property
}
