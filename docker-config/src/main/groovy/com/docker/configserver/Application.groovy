package com.docker.configserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
class Application {
    static void main(String[] args) {
        SpringApplication.run Application, args
    }
}
