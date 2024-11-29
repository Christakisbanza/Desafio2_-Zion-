package com.example.microservico_a.controller;
import com.example.microservico_a.controller.dto.PostCreateDto;
import com.example.microservico_a.controller.dto.PostResponseDto;
import com.example.microservico_a.controller.exception.ErrorMessage;
import com.example.microservico_a.controller.mapper.PostMapper;
import com.example.microservico_a.entities.Post;
import com.example.microservico_a.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController implements Serializable {

    @Autowired
    private PostService postService;

    @Operation(
            summary = "Publish a new post",
            description = "Resource to create a new Post",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Post created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @PostMapping
    public ResponseEntity<PostResponseDto> create(@RequestBody @Valid PostCreateDto postCreateDto){
        Post post = postService.publicPost(postCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PostMapper.toDto(post));
    }


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

}