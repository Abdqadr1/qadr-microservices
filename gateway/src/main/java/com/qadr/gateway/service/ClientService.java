package com.qadr.gateway.service;

import com.qadr.gateway.error.CustomException;
import com.qadr.gateway.model.Client;
import com.qadr.gateway.model.ClientDetails;
import com.qadr.gateway.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Service
public class ClientService implements ReactiveUserDetailsService {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Mono<Client> saveClient(Client client){
        String secret =  generateRandomString();
        client.setClientSecret(secret);
        client.setClientSecretSecret(passwordEncoder.encode(secret));
        return clientRepo.findByClientNameIgnoreCase(client.getClientName())
                .flatMap(c -> {
                    if(Objects.nonNull(c))
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Client already exists"));
                    return Mono.just(c);
                })
                .then(clientRepo.save(client));
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

    public Mono<Client> findByName(String name){
        return clientRepo.findByClientNameIgnoreCase(name)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Client does not exist")));
    }

    public Mono<Void> deleteClientById(String id){
        return findClientById(id)
                .then(clientRepo.deleteById(id));
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return findByName(username).map(ClientDetails::new);
    }

    public Flux<Client> getAllClients(){
        return clientRepo.findAll();
    }

    private String generateRandomString(){
        return UUID.randomUUID().toString()
                .replace("-", "")
                .replace("_", "");
    }


}
