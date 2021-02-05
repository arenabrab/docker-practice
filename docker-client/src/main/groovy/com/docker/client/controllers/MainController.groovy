package com.docker.client.controllers

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class MainController {

    @Value('${andrew.thing}')
    String zone

    @GetMapping("/home")
    @CrossOrigin("http://localhost:3000")
    @HystrixCommand(fallbackMethod = "hystrixFallback")
    def getot() {
        zone
    }

    @KafkaListener(topics = 'refresh-config', groupId = "docker-test-id")
    static def listen(String str) {
        log.info(str)
    }

    static def hystrixFallback() {
        log.info("this is the hystrix fallback")
    }

}
