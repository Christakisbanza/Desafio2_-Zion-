package com.example.microservico_b.repository;

import com.example.microservico_b.model.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}