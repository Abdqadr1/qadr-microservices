package com.qadr.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class BankApplication {


    // this bean turn on the httptrace actuator endpoint that has been turned off by default
    @Bean
    public HttpTraceRepository htttpTraceRepository()
    {
        return new InMemoryHttpTraceRepository();
    }

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }
}
