package com.example.microservico_b.service;

import com.example.microservico_b.client.JsonPlaceholderClient;
import com.example.microservico_b.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.microservico_b.model.entities.Post;

@Service
public class PostService {

    @Autowired
    private JsonPlaceholderClient jsonPlaceholderClient;

    @Autowired
    private PostRepository postRepository;

    public Post save(Post post){
        return postRepository.save(post);
    }

}