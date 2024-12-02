package com.example.microservico_a.controller;

import com.example.microservico_a.controller.dto.CommentCreateDto;
import com.example.microservico_a.controller.dto.CommentResponseDto;
import com.example.microservico_a.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts/{postId}")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Integer postId,
                                                            @Valid @RequestBody CommentCreateDto commentCreateDto) {

        CommentResponseDto responseDto = commentService.save(postId, commentCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Integer postId) {
        List<CommentResponseDto> responseDtos = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Integer postId, @PathVariable String id) {
        CommentResponseDto responseDto = commentService.findById(postId, id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Integer postId, @PathVariable String id,
                                                            @Valid @RequestBody CommentCreateDto commentCreateDto) {

        CommentResponseDto responseDto = commentService.update(postId, id, commentCreateDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer postId, @PathVariable String id) {
        commentService.delete(postId, id);
        return ResponseEntity.noContent().build();
    }
}
