package com.qadr.country;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class CountryApplication {


    // this bean turn on the httptrace actuator endpoint that has been turned off by default
    @Bean
    public HttpTraceRepository htttpTraceRepository()
    {
        return new InMemoryHttpTraceRepository();
    }

    public static void main(String[] args) {
        SpringApplication.run(CountryApplication.class, args);
    }
}
