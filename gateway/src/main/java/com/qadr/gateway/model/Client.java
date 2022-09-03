package com.qadr.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Indexed(unique = true)
    private String clientName;

    private String clientSecret;

    @JsonIgnore
    private String clientSecretSecret;

    private List<String> roles;
}
