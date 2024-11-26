package com.example.microservico_b.service;

import com.example.microservico_b.client.JsonPlaceholderClient;
import com.example.microservico_b.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.microservico_b.model.entities.Post;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PostService {

    @Autowired
    private JsonPlaceholderClient jsonPlaceholderClient;

    @Autowired
    private PostRepository postRepository;

    public Post save(Post post){
        return postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);

    }

}