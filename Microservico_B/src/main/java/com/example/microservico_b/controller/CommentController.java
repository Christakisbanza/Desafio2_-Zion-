package com.example.microservico_b.controller;

import com.example.microservico_b.controller.dto.CommentCreateDto;
import com.example.microservico_b.controller.dto.CommentResponseDto;
import com.example.microservico_b.controller.exception.ErrorMessage;
import com.example.microservico_b.controller.mapper.CommentMapper;
import com.example.microservico_b.model.entities.Comment;
import com.example.microservico_b.service.CommentService;
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
        List<Comment> comments = commentService.findByPostId(postId);

        List<CommentResponseDto> commentDtos = comments.stream().map(CommentMapper::toDto).toList();

        return ResponseEntity.ok(commentDtos);
    }

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
    public ResponseEntity<CommentResponseDto> create(@PathVariable Integer postId, @RequestBody @Valid CommentCreateDto commentCreateDto) {
        Comment comment = commentService.save(postId, CommentMapper.toComment(commentCreateDto));
        CommentResponseDto responseDto = CommentMapper.toDto(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
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
    public ResponseEntity<Void> delete(@PathVariable Integer postId, @PathVariable String id) {
        commentService.delete(postId, id);

        return ResponseEntity.noContent().build();
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
    public ResponseEntity<CommentResponseDto> update(@PathVariable Integer postId, @PathVariable String id,
                                                     @RequestBody @Valid CommentCreateDto commentCreateDto) {
        Comment commentToUpdate = CommentMapper.toComment(commentCreateDto);
        commentToUpdate.setId(id);

        Comment updatedComment = commentService.update(postId, commentToUpdate);
        CommentResponseDto responseDto = CommentMapper.toDto(updatedComment);

        return ResponseEntity.ok(responseDto);
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
    public ResponseEntity<CommentResponseDto> getById(@PathVariable Integer postId, @PathVariable String id) {
        Comment comment = commentService.findById(postId, id);
        CommentResponseDto responseDto = CommentMapper.toDto(comment);

        return ResponseEntity.ok(responseDto);
    }
}
