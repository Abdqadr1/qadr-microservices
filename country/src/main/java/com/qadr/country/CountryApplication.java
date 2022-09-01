package com.qadr.country;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CountryApplication {
    public static void main(String[] args) {
        SpringApplication.run(CountryApplication.class, args);
    }
}
