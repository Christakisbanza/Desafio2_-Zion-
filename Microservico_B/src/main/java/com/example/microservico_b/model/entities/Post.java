package com.example.microservico_b.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Post {

    @Id
    @JsonIgnore
    private String id;

    @UniqueElements
    @JsonProperty("id")
    private Long postId;

    @JsonProperty("userId")
    private Long userId;

    private String title;
    private String body;
}
