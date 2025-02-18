package com.example.microservico_b.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PostResponseDto {
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
}
