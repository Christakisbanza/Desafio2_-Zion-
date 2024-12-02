package com.example.microservico_b.controller;

import com.example.microservico_b.controller.dto.PostCreateDto;
import com.example.microservico_b.controller.dto.PostResponseDto;
import com.example.microservico_b.controller.exception.ErrorMessage;
import com.example.microservico_b.controller.mapper.PostMapper;
import com.example.microservico_b.model.entities.Post;
import com.example.microservico_b.service.PostService;
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
            summary = "Retrieve all Posts",
            description = "Endpoint to retrieve all Posts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Posts retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Data not found",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        List<Post> post = postService.findAll();
        return ResponseEntity.ok().body(post);
    }

    

    @Operation(
            summary = "Sync data from JSON Placeholder API",
            description = "Endpoint to insert Posts from the JSON Placeholder API into the database",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Posts inserted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @PostMapping("/sync-data")
    public List<Post> syncData() {
        return postService.syncData();
    }



    @Operation(
            summary = "Create a new Post",
            description = "Endpoint to create a new Post",
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
        Post post = postService.save(PostMapper.toPost(postCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(PostMapper.toDto(post));
    }



    @Operation(
            summary = "Update Post by Id",
            description = "Update a Post by its Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Post updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Post not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@RequestBody @Valid PostCreateDto postCreateDto,
                                                  @PathVariable int id) {

        Post postToUpdate = PostMapper.toPost(postCreateDto);
        postToUpdate.setId(id);
        Post updatedPost = postService.update(postToUpdate);

        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toDto(updatedPost));
    }



    @Operation(
            summary = "Delete a Post",
            description = "Endpoint to delete a Post",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Post deleted successfully"
                    )
            }

    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        postService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }



    @Operation(
            summary = "Retrieve a Post by ID",
            description = "Endpoint to find a Post by its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Post retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Post not found",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable int id) {
        Post post = postService.getById(id);
        return ResponseEntity.ok(post);
    }
}