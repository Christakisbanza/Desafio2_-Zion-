package com.example.microservico_a.controller.mapper;

import com.example.microservico_a.controller.dto.PostCreateDto;
import com.example.microservico_a.controller.dto.PostResponseDto;
import com.example.microservico_a.entities.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostMapper {

    public static Post toPost(PostCreateDto postCreateDto){
        return new ModelMapper().map(postCreateDto, Post.class);
    }

    public static PostResponseDto toDto(Post post){
        return new ModelMapper().map(post, PostResponseDto.class);
    }
}
