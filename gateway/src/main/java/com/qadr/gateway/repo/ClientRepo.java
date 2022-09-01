package com.qadr.gateway.repo;

import com.qadr.gateway.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends ReactiveMongoRepository<Client, String> {
}
