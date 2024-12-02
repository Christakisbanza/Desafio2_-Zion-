package com.example.microservico_a.client;

import com.example.microservico_a.controller.dto.CommentCreateDto;
import com.example.microservico_a.controller.dto.CommentResponseDto;
import com.example.microservico_a.entities.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "jsonPlaceholderClient", url = "http://localhost:8081/api/posts")
public interface JsonPlaceholderClient {

    @GetMapping
    List<Post> getPosts();

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable int id);

    @GetMapping("/{id}")
    ResponseEntity<Post> findById(@PathVariable int id);

    @PutMapping("/{id}")
    Post update(@PathVariable int id, @RequestBody Post post);

    // comentarios
    @GetMapping("/{postId}/comments")
    List<CommentResponseDto> getCommentsByPostId(@PathVariable Integer postId);

    @PostMapping("/{postId}/comments")
    CommentResponseDto createComment(@PathVariable Integer postId, @RequestBody CommentCreateDto commentCreateDto);

    @DeleteMapping("/{postId}/{id}")
    void deleteComment(@PathVariable Integer postId, @PathVariable String id);

    @PutMapping("/{postId}/{id}")
    CommentResponseDto updateComment(@PathVariable Integer postId, @PathVariable String id,
                                     @RequestBody CommentCreateDto commentCreateDto);

    @GetMapping("/{postId}/{id}")
    CommentResponseDto getCommentById(@PathVariable Integer postId, @PathVariable String id);
}
