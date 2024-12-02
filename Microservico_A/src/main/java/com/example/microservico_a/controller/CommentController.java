package com.example.microservico_a.controller;

import com.example.microservico_a.controller.dto.CommentCreateDto;
import com.example.microservico_a.controller.dto.CommentResponseDto;
import com.example.microservico_a.controller.exception.ErrorMessage;
import com.example.microservico_a.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Create a new Comment for a Post",
            description = "Endpoint to create a new Comment for a Post",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comment successfully created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid data for creating the comment",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Integer postId,
                                                            @Valid @RequestBody CommentCreateDto commentCreateDto) {

        CommentResponseDto responseDto = commentService.save(postId, commentCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(
            summary = "Retrieve all Comments from a Post",
            description = "Endpoint to retrieve all Comments from a Post",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comments successfully retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Post not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Integer postId) {
        List<CommentResponseDto> responseDtos = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(responseDtos);
    }

    @Operation(
            summary = "Retrieve a Comment by ID",
            description = "Endpoint to retrieve a specific Comment by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comment successfully retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Comment not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Integer postId, @PathVariable String id) {
        CommentResponseDto responseDto = commentService.findById(postId, id);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Update a Comment",
            description = "Endpoint to update a Comment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comment successfully updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Comment not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Integer postId, @PathVariable String id,
                                                            @Valid @RequestBody CommentCreateDto commentCreateDto) {

        CommentResponseDto responseDto = commentService.update(postId, id, commentCreateDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Delete a Comment",
            description = "Endpoint to delete a Comment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comment successfully deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Comment not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer postId, @PathVariable String id) {
        commentService.delete(postId, id);
        return ResponseEntity.noContent().build();
    }
}
