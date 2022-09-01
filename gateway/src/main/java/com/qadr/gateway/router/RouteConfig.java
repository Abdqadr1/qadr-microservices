package com.qadr.gateway.router;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", r -> r.path("/get")
                        .uri("http://httpbin.org"))
//                .route("country", r -> r.path("/country/**")
//                        .uri("lb://country"))
//                .route("bank", r -> r.path("/bank/**")
//                        .uri("lb://bank"))
                .route("country", r -> r.path("/api/country/**")
                        .filters(f -> f.rewritePath("/api/country/(?<segment>.*)", "/country/${segment}"))
                        .uri("lb://country"))
                .route("bank", r -> r.path("/api/bank/**")
                        .filters(f -> f.rewritePath("/api/bank/(?<segment>.*)", "/bank/${segment}"))
                        .uri("lb://bank"))
                .build();
    }
}
