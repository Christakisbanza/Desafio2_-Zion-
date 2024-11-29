package com.example.microservico_a.client;

import com.example.microservico_a.controller.dto.PostCreateDto;
import com.example.microservico_a.entities.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "jsonPlaceholderClient", url = "http://localhost:8081/api/posts")
public interface JsonPlaceholderClient {

    @GetMapping("/posts")
    List<Post> getPosts();



    @GetMapping("/{id}")
    ResponseEntity<Post> findById(@PathVariable int id);

    @PostMapping
    ResponseEntity<Post>publicPost(@RequestBody PostCreateDto dto);
}