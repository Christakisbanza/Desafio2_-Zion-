package com.example.microservico_a.repository;

import com.example.microservico_a.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, Integer> {
    Optional<Post> findTopByOrderByIdDesc();
}