package com.example.microservico_a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroservicoAApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicoAApplication.class, args);
    }

}
