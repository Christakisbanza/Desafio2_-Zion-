package com.example.microservico_a.controller;
import com.example.microservico_a.entities.Post;
import com.example.microservico_a.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController implements Serializable {

    @Autowired
    private PostService postService;

    @GetMapping("/ms-a")
    public ResponseEntity<List<Post>> getAll(){
        List<Post> posts = postService.getAll();
        return ResponseEntity.ok().body(posts);
    }
}