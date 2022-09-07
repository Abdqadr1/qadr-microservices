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
                // actuator endpoints
                .route("country_actuator", r -> r.path("/api/country/actuator/**")
                        .filters(f -> f.rewritePath("/api/country/actuator/(?<segment>.*)", "/actuator/${segment}"))
                        .uri("lb://country"))
                .route("bank_actuator", r -> r.path("/api/bank/actuator/**")
                        .filters(f -> f.rewritePath("/api/bank/actuator/(?<segment>.*)", "/actuator/${segment}"))
                        .uri("lb://bank"))
                //business login endpoints
                .route("country", r -> r.path("/api/country/**")
                        .filters(f -> f.rewritePath("/api/country/(?<segment>.*)", "/country/${segment}"))
                        .uri("lb://country"))
                .route("bank", r -> r.path("/api/bank/**")
                        .filters(f -> f.rewritePath("/api/bank/(?<segment>.*)", "/bank/${segment}"))
                        .uri("lb://bank"))
                .build();
    }
}
