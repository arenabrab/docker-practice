package com.docker.turbine

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.turbine.EnableTurbine

@SpringBootApplication
@EnableTurbine
class Application {
    static void main(String[] args) {
        SpringApplication.run Application, args
    }
}
