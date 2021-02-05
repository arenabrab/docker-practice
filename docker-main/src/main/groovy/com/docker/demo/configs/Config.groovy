package com.docker.demo.configs

import com.docker.demo.services.OrderService
import com.docker.demo.services.ShopWorkService
import com.docker.demo.services.products.EarringService
import com.docker.demo.services.products.GemsService
import com.docker.demo.services.products.NecklaceService
import com.docker.demo.services.products.RingService
import groovy.util.logging.Slf4j
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

import static org.springframework.web.reactive.function.server.RequestPredicates.*
import static org.springframework.web.reactive.function.server.RouterFunctions.route
import static org.springframework.web.reactive.function.server.ServerResponse.ok
import static reactor.core.publisher.Mono.just

@Slf4j
//@EnableWebFluxSecurity
@EnableWebFlux
@EnableEurekaClient
@EnableHystrix
@Configuration
class Config implements WebFluxConfigurer{

    RingService ringService
    NecklaceService necklaceService
    ShopWorkService shopWorkService
    EarringService earringService
    GemsService gemsService
    OrderService orderService

    Config(RingService ringService,
           ShopWorkService shopWorkService,
           NecklaceService necklaceService,
           EarringService earringService,
           GemsService gemsService,
           OrderService orderService) {
        this.ringService = ringService
        this.shopWorkService = shopWorkService
        this.necklaceService = necklaceService
        this.earringService = earringService
        this.gemsService = gemsService
        this.orderService = orderService
    }

    @Override
    void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
    }

    @Bean
    RouterFunction basicRoutes() {
        route(GET("/"), this.&home)
    }

    @Bean
    RouterFunction ringRoutes() {
        route(GET('/rings/{id}'), ringService.&getOne)
                .andRoute(GET("/rings"), ringService.&getAll)
                .andRoute(POST("/rings"), ringService.&addOne)
                .andRoute(PATCH("/rings"), ringService.&update)
                .andRoute(POST("/sortedRings"), ringService.&getSortedRings)
                .andRoute(DELETE("/rings"), ringService.&removeOne)
                .andRoute(GET("/rings/deleteAll"), ringService.&removeAll)
    }

    @Bean
    RouterFunction serviceRoutes() {
        route(GET('/services/{id}'), shopWorkService.&getOne)
                .andRoute(GET("/services"), shopWorkService.&findAllShopWork)
                .andRoute(DELETE("/services"), shopWorkService.&removeShopWork)
                .andRoute(POST("/services"), shopWorkService.&addShopWork)
                .andRoute(PATCH("/services"), shopWorkService.&updateShopWork)
                .andRoute(GET("/services/deleteAll"), shopWorkService.&removeAll)
    }

    @Bean
    RouterFunction NecklaceRoutes() {
        route(GET('/necklaces/{id}'), necklaceService.&getOne)
                .andRoute(GET("/necklaces"), necklaceService.&getAll)
                .andRoute(POST("/necklaces"), necklaceService.&addOne)
                .andRoute(PATCH("/necklaces"), necklaceService.&update)
                .andRoute(POST("/sortedNecklaces"), necklaceService.&getSortedNecklaces)
                .andRoute(DELETE("/necklaces"), necklaceService.&removeOne)
                .andRoute(GET("/necklaces/deleteAll"), necklaceService.&removeAll)
    }

    @Bean
    RouterFunction EarringRoutes() {
        route(GET('/earrings/{id}'), earringService.&getOne)
                .andRoute(GET("/earrings"), earringService.&getAll)
                .andRoute(POST("/earrings"), earringService.&addOne)
                .andRoute(PATCH("/earrings"), earringService.&update)
                .andRoute(POST("/sortedEarrings"), earringService.&getSortedEarrings)
                .andRoute(DELETE("/earrings"), earringService.&removeOne)
                .andRoute(GET("/earrings/deleteAll"), earringService.&removeAll)
    }

    @Bean
    RouterFunction GemRoutes() {
        route(GET('/gems/{id}'), gemsService.&getOne)
                .andRoute(GET("/gems"), gemsService.&getAll)
                .andRoute(PATCH("/gems"), gemsService.&update)
                .andRoute(POST("/sortedGems"), gemsService.&getSortedGems)
                .andRoute(POST("/gems"), gemsService.&addOne)
                .andRoute(DELETE("/gems"), gemsService.&removeOne)
                .andRoute(GET("/gems/deleteAll"), gemsService.&removeAll)
    }

    @Bean
    RouterFunction orderRoutes() {
        route(GET("/orders/{id}"), orderService.&getOne)
                .andRoute(GET("/orders"), orderService.&getAll)
//                .andRoute(PATCH("/orders"), orderService.&update)
//                .andRoute(POST("/sortedGems"), orderService.&getSortedGems)
                .andRoute(POST("/orders"), orderService.&addOne)
                .andRoute(DELETE("/orders"), orderService.&removeOne)
                .andRoute(GET("/orders/deleteAll"), orderService.&removeAll)
    }

    static Mono<ServerResponse> home() {
        ok().body(just("Welcome!"), String)
    }

//    @Bean
//    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//            .authorizeExchange()
//        .pathMatchers("/do")
//        .permitAll()
////                .pathMatchers("/add")
////                .hasAnyAuthority("USER")
//            .anyExchange()
//                .permitAll()
//            .and()
//                .build()
//    }
//
//    @Bean
//    ReactiveUserDetailsService userDetailsService(com.docker.demo.repositories.users.UserRepo repo) {
//        new ReactiveUserDetailsService() {
//            @Override
//            Mono<UserDetails> findByUsername(String username) {
//                repo.findByUsername(username)
//            }
//        }
//    }
}
