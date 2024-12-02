package com.example.microservico_a.service;

import com.example.microservico_a.client.JsonPlaceholderClient;
import com.example.microservico_a.controller.dto.CommentCreateDto;
import com.example.microservico_a.controller.dto.CommentResponseDto;
import com.example.microservico_a.exception.CommentNotFoundException;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private JsonPlaceholderClient commentClient;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCommentsByPostId() {
        Integer postId = 1;
        List<CommentResponseDto> expectedComments = List.of(
                new CommentResponseDto("1", postId, "User1", "user1@example.com", "Content1"),
                new CommentResponseDto("2", postId, "User2", "user2@example.com", "Content2")
        );

        when(commentClient.getCommentsByPostId(postId)).thenReturn(expectedComments);

        List<CommentResponseDto> actualComments = commentService.getCommentsByPostId(postId);

        assertEquals(expectedComments, actualComments);
        verify(commentClient, times(1)).getCommentsByPostId(postId);
    }

    @Test
    void testSave() {
        Integer postId = 1;
        CommentCreateDto createDto = new CommentCreateDto("User1", "user1@example.com", "Content1");
        CommentResponseDto expectedResponse = new CommentResponseDto("1", postId, createDto.getName(), createDto.getEmail(), createDto.getBody());

        when(commentClient.createComment(postId, createDto)).thenReturn(expectedResponse);

        CommentResponseDto actualResponse = commentService.save(postId, createDto);

        assertEquals(expectedResponse, actualResponse);
        verify(commentClient, times(1)).createComment(postId, createDto);
    }

    @Test
    void testFindById_CommentExists() {
        Integer postId = 1;
        String commentId = "1";
        CommentResponseDto expectedComment = new CommentResponseDto(commentId, postId, "User1", "user1@example.com", "Content1");

        when(commentClient.getCommentById(postId, commentId)).thenReturn(expectedComment);

        CommentResponseDto actualComment = commentService.findById(postId, commentId);

        assertEquals(expectedComment, actualComment);
        verify(commentClient, times(1)).getCommentById(postId, commentId);
    }

    @Test
    void testFindById_CommentNotFound() {
        Integer postId = 1;
        String commentId = "99";

        when(commentClient.getCommentById(postId, commentId)).thenThrow(FeignException.NotFound.class);

        assertThrows(CommentNotFoundException.class, () -> commentService.findById(postId, commentId));
        verify(commentClient, times(1)).getCommentById(postId, commentId);
    }

    @Test
    void testUpdate() {
        Integer postId = 1;
        String commentId = "1";
        CommentCreateDto updateDto = new CommentCreateDto("UpdatedUser", "updated@example.com", "UpdatedContent");
        CommentResponseDto updatedResponse = new CommentResponseDto(commentId, postId, updateDto.getName(), updateDto.getEmail(), updateDto.getBody());

        when(commentClient.updateComment(postId, commentId, updateDto)).thenReturn(updatedResponse);

        CommentResponseDto actualResponse = commentService.update(postId, commentId, updateDto);

        assertEquals(updatedResponse, actualResponse);
        verify(commentClient, times(1)).updateComment(postId, commentId, updateDto);
    }

    @Test
    void testDelete() {
        Integer postId = 1;
        String commentId = "1";

        doNothing().when(commentClient).deleteComment(postId, commentId);

        commentService.delete(postId, commentId);

        verify(commentClient, times(1)).deleteComment(postId, commentId);
    }
}
