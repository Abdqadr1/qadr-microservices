package com.qadr.gateway.service;

import com.qadr.gateway.error.CustomException;
import com.qadr.gateway.model.Client;
import com.qadr.gateway.model.ClientDetails;
import com.qadr.gateway.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ClientService implements ReactiveUserDetailsService {

    @Autowired
    private ClientRepo clientRepo;

    public Mono<Client> saveClient(Client client){
        return clientRepo.save(client);
    }

    public Mono<Client> findClientById(String id){
        return clientRepo.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(
                        HttpStatus.BAD_REQUEST,
                        String.format("client with id '%s' not found", id))
                ));
    }

    public Mono<Client> findByClientSecret(String secret){
        return clientRepo.findByClientSecret(secret)
                .switchIfEmpty(Mono.error(new CustomException(
                        HttpStatus.BAD_REQUEST,
                        String.format("client with secret '%s' not found", secret))
                ));
    }

    public Mono<Void> deleteClientById(String id){
        return findClientById(id)
                .then(clientRepo.deleteById(id));
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return clientRepo.findByClientNameIgnoreCase(username)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Client does not exist")))
                .map(ClientDetails::new);
    }
}
