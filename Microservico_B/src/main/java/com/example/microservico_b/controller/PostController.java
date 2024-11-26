package com.example.microservico_b.controller;

import com.example.microservico_b.controller.dto.PostCreateDto;
import com.example.microservico_b.controller.dto.PostResponseDto;
import com.example.microservico_b.controller.mapper.PostMapper;
import com.example.microservico_b.model.entities.Post;
import com.example.microservico_b.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> create(@RequestBody @Valid PostCreateDto postCreateDto){
        Post post = postService.save(PostMapper.toPost(postCreateDto));
        return ResponseEntity.ok().body(PostMapper.toDto(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        try {
            postService.delete(id);
            return ResponseEntity.ok("Deleted with Success");
        }catch(Exception ex) {
            return new ResponseEntity("Query Error", HttpStatusCode.valueOf(504));
        }
    }
}