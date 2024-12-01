package com.example.microservico_a.service;

import com.example.microservico_a.client.JsonPlaceholderClient;
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


    public Post findById(int id) {
        ResponseEntity<Post> response = jsonPlaceholderClient.findById(id);

        if (response == null || !response.hasBody()) {
            throw new PostNotFoundException("Value not found");
        }

        return response.getBody();
    }

    public void deleteById(int id){
        findById(id);
        jsonPlaceholderClient.deleteById(id);
    }

    public Post updateById(int id, Post post) {
        findById(id);
        Post updatedPost = jsonPlaceholderClient.update(id, post);

        if (updatedPost == null) {
            throw new PostNotFoundException("Failed to update post with id " + id);
        }
        return updatedPost;
    }
}