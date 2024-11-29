package com.example.microservico_a.service;

import com.example.microservico_a.client.JsonPlaceholderClient;
import com.example.microservico_a.controller.dto.PostCreateDto;
import com.example.microservico_a.entities.Post;
import com.example.microservico_a.exception.PostNotFoundException;
import com.example.microservico_a.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private JsonPlaceholderClient jsonPlaceholderClient;

    @Autowired
    private PostRepository postRepository;

    public Post findById(int id) {
        ResponseEntity<Post> response = jsonPlaceholderClient.findById(id);

        if (response == null || !response.hasBody()) {
            throw new PostNotFoundException("The value was not found");
        }

        return response.getBody();
    }

    public Post publicPost(PostCreateDto dto){
        ResponseEntity<Post> response = jsonPlaceholderClient.publicPost(dto);
        if(response == null || response.getStatusCode().equals(422)){
            throw new RuntimeException("Cannot create the post");
        }
        return response.getBody();
    }


}