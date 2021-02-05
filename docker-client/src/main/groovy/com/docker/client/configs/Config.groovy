package com.docker.client.configs

import com.docker.client.entities.Product
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

import static org.springframework.web.reactive.function.server.RequestPredicates.GET
import static org.springframework.web.reactive.function.server.RouterFunctions.route
import static org.springframework.web.reactive.function.server.ServerResponse.ok
import static reactor.core.publisher.Mono.just

@Slf4j
@Configuration
@EnableEurekaClient
@EnableHystrix
@EnableFeignClients
class Config implements WebFluxConfigurer {

    @Value('${base.uri.server}')
    String uri

    @Override
    void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
    }

    @Bean
    @LoadBalanced
    WebClient.Builder webClient() {
        WebClient.builder()
    }

    @Bean
    RouterFunction routerFunction() {
        route(GET("/do"), this.&postProduct)
                .andRoute(GET("/doit"), this.&getTwo)
    }

    Mono<ServerResponse> postProduct(ServerRequest request) {
        log.info("IN post product")
        Mono<Product> product = just(new Product(name: "testProduct", price: BigDecimal.valueOf(10.50), pageViews: 150))
        ok().body(webClient()
                .build()
                .post()
                .uri("${uri}/addOne")
                .body(product, Product)
                .retrieve()
                .bodyToMono(Product).log(), Product)
    }

    Mono<ServerResponse> getTwo(ServerRequest request) {
        ok().body(webClient()
                .build()
                .get()
                .uri("${uri}/rings")
                .retrieve()
                .bodyToFlux(Product).log(), Product)
    }

}
