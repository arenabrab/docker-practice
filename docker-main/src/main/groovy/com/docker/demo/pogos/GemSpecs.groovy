package com.docker.demo.pogos

import com.docker.demo.enums.Rating
import groovy.transform.Canonical

@Canonical
class GemSpecs {
    Rating cut
    String color
    String clarity
    String certification
}
