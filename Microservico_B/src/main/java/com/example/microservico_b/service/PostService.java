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
        Integer maxId = postRepository.findAll()
                .stream()
                .mapToInt(Post::getId)
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
        Integer maxId = postRepository.findAll()
                .stream()
                .mapToInt(Post::getId)
                .max()
                .orElse(0);

        post.setId(maxId + 1);

        return postRepository.save(post);
    }

    public void delete(int id) {
        postRepository.deleteById(id);
    }
    public Post buscaPorId(int id) {
        return postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post not found")
        );
    }

    public Post update(Post post) {
       return postRepository.save(post);
    }
}