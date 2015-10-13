package com.seitenbau.microservices.composite.mailbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class MailboxServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailboxServiceApplication.class, args);
    }
}
