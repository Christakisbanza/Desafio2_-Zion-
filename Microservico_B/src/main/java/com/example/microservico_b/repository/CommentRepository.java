package com.example.microservico_b.repository;

import com.example.microservico_b.model.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(Integer postId);
}
