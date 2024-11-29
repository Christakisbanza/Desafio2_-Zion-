package com.example.microservico_a.controller;
import com.example.microservico_a.controller.dto.PostResponseDto;
import com.example.microservico_a.controller.exception.ErrorMessage;
import com.example.microservico_a.entities.Post;
import com.example.microservico_a.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api/posts/ms-a")
public class PostController implements Serializable {

    @Autowired
    private PostService postService;




    @Operation(
            summary = "Retrieve by Id",
            description = "Find a Post by Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resource retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable int id) {
        Post post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}