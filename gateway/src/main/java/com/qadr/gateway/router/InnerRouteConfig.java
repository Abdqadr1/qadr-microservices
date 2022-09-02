package com.qadr.gateway.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class InnerRouteConfig {

    @Autowired
    private RouteHandler handler;


    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/", handler::authenticate)
                .POST("/client/create", handler::registerClient)
                .GET("/auth", handler::authenticate)
                .build();
    }
}
