package com.example.microservico_b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroservicoBApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicoBApplication.class, args);
    }

}