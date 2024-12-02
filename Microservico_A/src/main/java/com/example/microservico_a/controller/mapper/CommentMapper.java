package com.example.microservico_a.controller.mapper;

import com.example.microservico_a.controller.dto.CommentCreateDto;
import com.example.microservico_a.controller.dto.CommentResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.xml.stream.events.Comment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment toComment(CommentCreateDto commentCreateDto) {
        return new ModelMapper().map(commentCreateDto, Comment.class);
    }

    public static CommentResponseDto toDto(Comment comment) {
        return new ModelMapper().map(comment, CommentResponseDto.class);
    }
}
