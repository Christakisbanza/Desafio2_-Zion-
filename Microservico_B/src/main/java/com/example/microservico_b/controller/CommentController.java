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
            summary = "Buscar todos os Comentários de um Post",
            description = "Recurso para buscar todos os Comentários de um Post",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentários encontrados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Post não encontrado",
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
            summary = "Criar um novo Comentário em um Post",
            description = "Recurso para criar um novo Comentário em um Post",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comentário criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Dados inválidos para criação do comentário",
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
            summary = "Deletar um Comentário",
            description = "Recurso para deletar um Comentário",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentário deletado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Comentário não encontrado",
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
            summary = "Atualizar um Comentário",
            description = "Recurso para atualizar um Comentário",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentário atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Comentário não encontrado",
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
            summary = "Buscar um Comentário por ID",
            description = "Recurso para buscar um Comentário específico por ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentário encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Comentário não encontrado",
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
