package com.example.microservico_b.service;

import com.example.microservico_b.client.JsonPlaceholderClient;
import com.example.microservico_b.exception.PostNotFoundException;
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

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> syncData() {
        Integer maxId = postRepository.findTopByOrderByIdDesc()
                .map(Post::getId)
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
        Integer maxId = postRepository.findTopByOrderByIdDesc()
                .stream()
                .mapToInt(Post::getId)
                .max()
                .orElse(0);

        post.setId(maxId + 1);

        return postRepository.save(post);
    }

    public void delete(int id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException("Post with id " + id + " not found"));

        postRepository.delete(post);
    }

    public Post getById(int id) {
        return postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException("Post not found")
        );
    }

    public Post update(Post post) {
        Post existingPost = getById(post.getId());

        existingPost.setUserId(post.getUserId());
        existingPost.setTitle(post.getTitle());
        existingPost.setBody(post.getBody());

        return postRepository.save(existingPost);
    }
}