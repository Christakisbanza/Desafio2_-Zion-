package com.example.microservico_a.service;

import com.example.microservico_a.client.JsonPlaceholderClient;
import com.example.microservico_a.controller.dto.CommentCreateDto;
import com.example.microservico_a.controller.dto.CommentResponseDto;
import com.example.microservico_a.exception.CommentNotFoundException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private JsonPlaceholderClient commentClient;

    public List<CommentResponseDto> getCommentsByPostId(Integer postId) {
        return commentClient.getCommentsByPostId(postId);
    }

    public CommentResponseDto save(Integer postId, CommentCreateDto commentCreateDto) {
        return commentClient.createComment(postId, commentCreateDto);
    }

    public CommentResponseDto findById(Integer postId, String commentId) {
        try {
            return commentClient.getCommentById(postId, commentId);
        } catch (FeignException.NotFound e) {
            throw new CommentNotFoundException("Comment not found for postId " + postId + " and commentId " + commentId);
        }
    }

    public CommentResponseDto update(Integer postId, String commentId, CommentCreateDto commentCreateDto) {
        return commentClient.updateComment(postId, commentId, commentCreateDto);
    }

    public void delete(Integer postId, String commentId) {
        commentClient.deleteComment(postId, commentId);
    }
}
