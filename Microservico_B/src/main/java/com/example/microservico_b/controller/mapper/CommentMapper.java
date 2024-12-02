package com.example.microservico_b.controller.mapper;


import com.example.microservico_b.controller.dto.CommentCreateDto;
import com.example.microservico_b.controller.dto.CommentResponseDto;
import com.example.microservico_b.model.entities.Comment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment toComment(CommentCreateDto commentCreateDto) {
        return new ModelMapper().map(commentCreateDto, Comment.class);
    }

    public static CommentResponseDto toDto(Comment comment) {
        return new ModelMapper().map(comment, CommentResponseDto.class);
    }
}
