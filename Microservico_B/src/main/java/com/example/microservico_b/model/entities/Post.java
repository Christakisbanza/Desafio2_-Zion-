package com.example.microservico_b.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Post {

    @Id
    private Integer id;
    private Integer userId;
    private String title;
    private String body;

    @Override
    public boolean equals(Object obj){
    return EqualsBuilder.reflectionEquals(this, obj);
    }
}

