package com.docker.hystrix.config

import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard
import org.springframework.context.annotation.Configuration

@Configuration
@EnableHystrixDashboard
@EnableEurekaClient
class Config {
}
