package com.example.microservico_b.service;

import com.example.microservico_b.exception.CommentNotFoundException;
import com.example.microservico_b.exception.PostNotFoundException;
import com.example.microservico_b.model.entities.Comment;
import com.example.microservico_b.repository.CommentRepository;
import com.example.microservico_b.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private boolean doesPostExist(Integer postId) {
        return postRepository.existsById(postId);
    }

    public List<Comment> findByPostId(Integer postId) {
        if (!doesPostExist(postId)) {
            throw new PostNotFoundException("Post not found");
        }
        return commentRepository.findByPostId(postId);
    }

    public Comment save(Integer postId, Comment comment) {
        if (!doesPostExist(postId)) {
            throw new PostNotFoundException("Post not found");
        }

        comment.setPostId(postId);
        return commentRepository.save(comment);
    }

    public Comment findById(Integer postId, String id) {
        if (!doesPostExist(postId)) {
            throw new PostNotFoundException("Post not found");
        }

        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent() && commentOptional.get().getPostId().equals(postId)) {
            return commentOptional.get();
        } else {
            throw new CommentNotFoundException("Comment not found for this post");
        }
    }

    public Comment update(Integer postId, Comment comment) {
        if (!doesPostExist(postId)) {
            throw new PostNotFoundException("Post not found");
        }

        Optional<Comment> existingComment = commentRepository.findById(comment.getId());
        if (existingComment.isPresent() && existingComment.get().getPostId().equals(postId)) {
            comment.setPostId(postId);
            return commentRepository.save(comment);
        } else {
            throw new CommentNotFoundException("Comment not found for this post");
        }
    }

    public boolean delete(Integer postId, String id) {
        if (!doesPostExist(postId)) {
            throw new PostNotFoundException("Post not found");
        }

        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent() && commentOptional.get().getPostId().equals(postId)) {
            commentRepository.deleteById(id);
            return true;
        } else {
            throw new CommentNotFoundException("Comment not found for this post");
        }
    }
}
