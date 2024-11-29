package com.example.microservico_b.repository;

import com.example.microservico_b.model.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, Integer> {
    Optional<Post> findTopByOrderByIdDesc();
}
