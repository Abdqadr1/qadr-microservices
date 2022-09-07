package com.qadr.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityRepository securityRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http
                .cors().configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*"));
                    configuration.setAllowedMethods(List.of("*"));
                    configuration.setAllowedHeaders(List.of("*"));
                    return configuration;
                });
        http
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityRepository)
                .authorizeExchange(exchange ->
                        exchange
                                .pathMatchers("/client/**", "/clients",
                                        "/api/admin/**", "/api/admin/**",
                                        "/api/bank/actuator/**", "/api/country/actuator/**")
                                .hasAuthority("ADMIN")
                                .pathMatchers("/auth").permitAll()
                                .pathMatchers("/api/**").authenticated()
        );
        http.csrf().disable();
        http.formLogin().disable();
        return http.build();
    }
}
