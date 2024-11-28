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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController implements Serializable {

    @Autowired
    private PostService postService;

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
    public ResponseEntity<?> deletePost(@PathVariable int id){
        try {
            postService.delete(id);
            return ResponseEntity.ok("Deleted with Success");
        }catch(Exception ex) {
            return new ResponseEntity("Query Error", HttpStatusCode.valueOf(504));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable int id) {
        Post post = postService.buscaPorId(id);
        return ResponseEntity.ok(post);
    }
}