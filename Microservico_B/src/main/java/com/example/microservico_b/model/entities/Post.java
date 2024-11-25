package com.example.microservico_b.model.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Document
public class Post {

    private Long id;
    private String title;
    private String description;
    
}
