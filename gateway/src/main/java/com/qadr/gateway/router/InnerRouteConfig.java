package com.qadr.gateway.router;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class InnerRouteConfig {


    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/", null)
                .build();
    }
}
