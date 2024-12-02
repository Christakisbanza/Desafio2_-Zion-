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
            summary = "Create a Post",
            description = "Resource to criete a new Post",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resourse created with sucess",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Data input inválid",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @PostMapping
    public ResponseEntity<PostResponseDto> create(@RequestBody @Valid PostCreateDto postCreateDto){
        Post post = postService.save(PostMapper.toPost(postCreateDto));
        return ResponseEntity.ok().body(PostMapper.toDto(post));
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


    
    @Operation(
            summary = "Delete by Id",
            description = "Delete a Post by Id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Resource deleted successfully"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<PostResponseDto> updateById(@PathVariable int id, @RequestBody @Valid PostCreateDto postCreateDto) {
        Post postToUpdate = PostMapper.toPost(postCreateDto);

        Post updatedPost = postService.updateById(id, postToUpdate);

        return ResponseEntity.ok(PostMapper.toDto(updatedPost));
    }
    @Operation(
            summary = "Get All Posts",
            description = "Retrieve all posts from the external service (microservice B)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of posts retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

}
