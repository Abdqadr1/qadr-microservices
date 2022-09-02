package com.qadr.gateway.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "clients")
@Data
public class Client {
    @Id
    private String id;

    @Indexed
    private String clientName;

    @Indexed
    private String clientSecret;

    private List<String> roles;
}
