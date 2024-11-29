package com.example.microservico_a.client;

import com.example.microservico_a.entities.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "jsonPlaceholderClient", url = "http://localhost:8081/api/posts")
public interface JsonPlaceholderClient {

    @GetMapping
    List<Post> getPosts();

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable int id);


    @GetMapping("/{id}")
    ResponseEntity<Post> findById(@PathVariable int id);

}