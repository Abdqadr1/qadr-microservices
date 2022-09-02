package com.qadr.gateway.repo;

import com.qadr.gateway.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepo extends ReactiveMongoRepository<Client, String> {

    Mono<Client> findByClientNameIgnoreCase(String clientName);

    Mono<Client> findByClientSecret(String secret);

}
