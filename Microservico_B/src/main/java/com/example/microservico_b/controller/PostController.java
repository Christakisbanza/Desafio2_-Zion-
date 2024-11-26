package com.example.microservico_b.controller;

import com.example.microservico_b.model.entities.Post;
import com.example.microservico_b.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;




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