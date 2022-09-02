package com.qadr.gateway.router;

import com.qadr.gateway.error.CustomException;
import com.qadr.gateway.model.Client;
import com.qadr.gateway.security.AuthenticationManager;
import com.qadr.gateway.security.JWTUtil;
import com.qadr.gateway.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
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

    public Mono<ServerResponse> authenticate(ServerRequest request){
        String name = request.queryParam("name")
                .orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "Incomplete parameter(s)"));
        String secret = request.queryParam("secret")
                .orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "Incomplete parameter(s)"));
        return clientService.findByUsername(name)
                .subscribeOn(Schedulers.parallel())
                .flatMap(userDetails -> {
                    if(!passwordEncoder.matches(secret, userDetails.getPassword())){
                        throw new CustomException(HttpStatus.BAD_REQUEST, "Bad Credentials");
                    }
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                    return authenticationManager.authenticate(authenticationToken);
                })
                .flatMap(auth -> {
                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", JWTUtil.createAccessToken(auth, "/login"));
                    tokens.put("refresh_token", JWTUtil.createRefreshToken(auth));
                    return  ServerResponse.ok().bodyValue(tokens);
                });
    }

    public Mono<ServerResponse> registerClient(ServerRequest request){
        return request.bodyToMono(Client.class)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Incomplete params")))
                .flatMap(clientService::saveClient)
                .flatMap(ServerResponse.ok()::bodyValue);
    }




}
