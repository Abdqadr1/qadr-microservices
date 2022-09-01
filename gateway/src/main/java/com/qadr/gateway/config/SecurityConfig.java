package com.qadr.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http.authorizeExchange(exchange ->
                        exchange.pathMatchers(
                                "/api/bank/admin/**",
                                        "/api/country/admin/**"
                                ).hasRole("QADR")
//                                .pathMatchers("/c/**").authenticated()
                                .pathMatchers("/api/**").permitAll());
//                .oauth2ResourceServer().jwt();
        http.csrf().disable();
        return http.build();
    }
}
