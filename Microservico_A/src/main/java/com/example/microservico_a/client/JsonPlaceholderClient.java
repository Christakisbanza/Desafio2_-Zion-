package com.example.microservico_a.client;

import com.example.microservico_a.controller.dto.PostCreateDto;
import com.example.microservico_a.entities.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "jsonPlaceholderClient", url = "http://localhost:8081/api/posts")
public interface JsonPlaceholderClient {

    @GetMapping
    List<Post> getPosts();

    @PostMapping
    Post create(@RequestBody Post post);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable int id);


    @GetMapping("/{id}")
    ResponseEntity<Post> findById(@PathVariable int id);

    @PutMapping("/{id}")
    Post update(@PathVariable int id, @RequestBody Post post);
}