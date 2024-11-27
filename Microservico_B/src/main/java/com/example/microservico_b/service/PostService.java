package com.example.microservico_b.service;

import com.example.microservico_b.client.JsonPlaceholderClient;
import com.example.microservico_b.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.microservico_b.model.entities.Post;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private JsonPlaceholderClient jsonPlaceholderClient;

    @Autowired
    private PostRepository postRepository;

    public List<Post> syncData() {
        Long maxId = postRepository.findAll()
                .stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0);

        List<Post> posts = jsonPlaceholderClient.getPosts();
        for (Post post : posts) {
            maxId++;
            post.setId(maxId);
        }

        postRepository.saveAll(posts);
        return posts;
    }

    public Post save(Post post){
        long maxId = postRepository.findAll()
                .stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0);

        post.setId(maxId + 1);

        return postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}