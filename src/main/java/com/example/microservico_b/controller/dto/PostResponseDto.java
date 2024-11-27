package com.example.microservico_b.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PostResponseDto {
    private String id;
    private String title;
    private String description;
}
