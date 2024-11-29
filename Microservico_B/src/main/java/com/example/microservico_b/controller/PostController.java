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
            summary = "Buscar todos os Posts",
            description = "Recurso para buscar todos os Posts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recurso achado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Dados não encontrados",
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
            summary = "Api Json Place Holder",
            description = "Recurso para inserir um Post da api Json Place Holder para o banco de dados",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Recurso inserido com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @PostMapping("/sync-data")
    public List<Post> syncData() {
        return postService.syncData();
    }



    @Operation(
            summary = "Criar um novo Post",
            description = "Recurso para criar um novo Post",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Dados de entrada inválidos",
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
            summary = "Alter the post with id",
            description = "Recurse for alter the post with a different id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Recurso atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@RequestBody @Valid PostCreateDto postCreateDto,
                                                  @PathVariable Integer id) {

        Post postToUpdate = PostMapper.toPost(postCreateDto);
        postToUpdate.setId(id);
        Post updatedPost = postService.update(postToUpdate);

        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toDto(updatedPost));
    }



    @Operation(
            summary = "Deletar um Post",
            description = "Recurso para deletar Post",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recurso deletado com sucesso"
                    )
            }

    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        postService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }



    @Operation(
            summary = "Recuperar por Id",
            description = "Achar um Post por Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso não encontrado ",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable int id) {
        Post post = postService.buscaPorId(id);
        return ResponseEntity.ok(post);
    }
}