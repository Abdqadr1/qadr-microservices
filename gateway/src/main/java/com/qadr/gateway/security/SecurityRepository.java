package com.qadr.gateway.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SecurityRepository implements ServerSecurityContextRepository {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    public static final String TOKEN_PREFIX = "Client ";

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public Mono load(ServerWebExchange swe) {
        ServerHttpRequest request = swe.getRequest();
        System.out.println(request.getPath());
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String accessToken = null;
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            accessToken = authHeader.replace(TOKEN_PREFIX, "");
        }else {
            log.warn("couldn't find client string, will ignore the header.");
        }
        if (accessToken != null) {
            try {
                DecodedJWT decodedJWT = jwtUtil.verifyToken(accessToken);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                List<SimpleGrantedAuthority> authorities = Arrays.stream(roles)
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);

            }catch (Exception e){
                Map<String, Object> ret = new HashMap<>();
                ret.put("message", e.getMessage());
                return Mono.just(ret);
            }
        } else {
            return Mono.empty();
        }
    }
}
