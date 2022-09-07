package com.qadr.gateway.router;

import com.qadr.gateway.error.CustomException;
import com.qadr.gateway.model.Client;
import com.qadr.gateway.security.AuthenticationManager;
import com.qadr.gateway.security.JWTUtil;
import com.qadr.gateway.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class RouteHandler  {
    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    public Mono<ServerResponse> authenticate(ServerRequest request){
        return request.bodyToMono(LoginRequest.class)
                .flatMap(loginRequest -> {
                    if( loginRequest.getSecret() == null || loginRequest.getName() == null
                        || loginRequest.getSecret().isBlank() || loginRequest.getName().isBlank()
                    )
                        throw new CustomException(HttpStatus.BAD_REQUEST, "Incomplete parameter(s)");
                    return clientService.findByUsername(loginRequest.getName())
                            .flatMap(userDetails -> {
                                if(!passwordEncoder.matches(loginRequest.getSecret(), userDetails.getPassword())){
                                    throw new CustomException(HttpStatus.BAD_REQUEST, "Bad Credentials");
                                }
                                var authToken = new UsernamePasswordAuthenticationToken(
                                        userDetails.getUsername(),
                                        null,
                                        userDetails.getAuthorities()
                                );
                                return authenticationManager.authenticate(authToken);
                            }).subscribeOn(Schedulers.parallel());
                }).flatMap(auth -> {
                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", jwtUtil.createAccessToken(auth, "/auth"));
                    tokens.put("refresh_token", jwtUtil.createRefreshToken(auth));
                    return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(tokens);
                });
    }

    public Mono<ServerResponse> registerClient(ServerRequest request){
        return request.bodyToMono(Client.class)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Incomplete params")))
                .flatMap(clientService::saveClient)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> getAllClients(ServerRequest request){
        return clientService.getAllClients()
                .collectList()
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> getClientByName(ServerRequest request){
        String name = request.queryParam("name")
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Incomplete parameter(s)"));
        return clientService.findByName(name)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteClient(ServerRequest request) {
        String id = Optional.of(request.pathVariable("id"))
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Incomplete parameter(s)"));
        return clientService.deleteClientById(id)
                .then(ServerResponse.ok().build());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class LoginRequest{
        private String name;
        private String secret;
    }
}
