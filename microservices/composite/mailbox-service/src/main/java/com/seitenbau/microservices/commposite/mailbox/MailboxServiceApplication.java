package com.seitenbau.microservices.commposite.mailbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MailboxServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailboxServiceApplication.class, args);
    }
}
