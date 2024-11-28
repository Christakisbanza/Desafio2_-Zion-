package com.example.microservico_b.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiCongig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Rest API - Desafio 2, Posts")
                                .description("API para gestão de Posts (Publicações)")
                                .version("V1")
                                .contact(new Contact().name("Christakis, Pedro, Felipe, André, Natanael"))
                );
    }
}
