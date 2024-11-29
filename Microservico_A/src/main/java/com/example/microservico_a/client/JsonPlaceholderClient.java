package com.example.microservico_a.client;

import com.example.microservico_a.entities.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "MS-A", url = "http://localhost:8081")
public interface JsonPlaceholderClient {

    @GetMapping("/api/posts")
    List<Post> getPosts();
}