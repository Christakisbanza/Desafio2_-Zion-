package com.example.microservico_b.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.microservico_b.exception.CommentNotFoundException;
import com.example.microservico_b.exception.PostNotFoundException;
import com.example.microservico_b.model.entities.Comment;
import com.example.microservico_b.repository.CommentRepository;
import com.example.microservico_b.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByPostId_PostNotFound() {
        Integer postId = 1;


        when(postRepository.existsById(postId)).thenReturn(false);


        assertThrows(PostNotFoundException.class, () -> {
            commentService.findByPostId(postId);
        });
    }

    @Test
    public void testFindByPostId_Success() {
        Integer postId = 1;
        Comment comment = new Comment("1", postId, "Comment Name", "email@example.com", "This is a comment");
        List<Comment> comments = Arrays.asList(comment);


        when(postRepository.existsById(postId)).thenReturn(true);
        when(commentRepository.findByPostId(postId)).thenReturn(comments);

        List<Comment> result = commentService.findByPostId(postId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment, result.get(0));
    }
    @Test
    public void testSave_PostNotFound() {
        Integer postId = 1;
        Comment comment = new Comment("1", postId, "New Comment", "email@example.com", "This is a new comment");


        when(postRepository.existsById(postId)).thenReturn(false);


        assertThrows(PostNotFoundException.class, () -> {
            commentService.save(postId, comment);
        });
    }

    @Test
    public void testSave_Success() {
        Integer postId = 1;
        Comment comment = new Comment("1", postId, "New Comment", "email@example.com", "This is a new comment");


        when(postRepository.existsById(postId)).thenReturn(true);
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment savedComment = commentService.save(postId, comment);

        assertNotNull(savedComment);
        assertEquals(comment, savedComment);
    }
    @Test
    public void testFindById_PostNotFound() {
        Integer postId = 1;
        String commentId = "1";


        when(postRepository.existsById(postId)).thenReturn(false);


        assertThrows(PostNotFoundException.class, () -> {
            commentService.findById(postId, commentId);
        });
    }

    @Test
    public void testFindById_CommentNotFound() {
        Integer postId = 1;
        String commentId = "1";


        when(postRepository.existsById(postId)).thenReturn(true);
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());


        assertThrows(CommentNotFoundException.class, () -> {
            commentService.findById(postId, commentId);
        });
    }

    @Test
    public void testFindById_Success() {
        Integer postId = 1;
        String commentId = "1";
        Comment comment = new Comment(commentId, postId, "Comment Name", "email@example.com", "This is a comment");


        when(postRepository.existsById(postId)).thenReturn(true);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Comment result = commentService.findById(postId, commentId);

        assertNotNull(result);
        assertEquals(comment, result);
    }
    @Test
    public void testUpdate_PostNotFound() {
        Integer postId = 1;
        Comment comment = new Comment("1", postId, "Updated Comment", "email@example.com", "Updated comment text");


        when(postRepository.existsById(postId)).thenReturn(false);


        assertThrows(PostNotFoundException.class, () -> {
            commentService.update(postId, comment);
        });
    }

    @Test
    public void testUpdate_CommentNotFound() {
        Integer postId = 1;
        Comment comment = new Comment("1", postId, "Updated Comment", "email@example.com", "Updated comment text");


        when(postRepository.existsById(postId)).thenReturn(true);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.empty());


        assertThrows(CommentNotFoundException.class, () -> {
            commentService.update(postId, comment);
        });
    }

    @Test
    public void testUpdate_Success() {
        Integer postId = 1;
        Comment comment = new Comment("1", postId, "Updated Comment", "email@example.com", "Updated comment text");


        when(postRepository.existsById(postId)).thenReturn(true);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment updatedComment = commentService.update(postId, comment);

        assertNotNull(updatedComment);
        assertEquals(comment, updatedComment);
    }
    @Test
    public void testDelete_Success() {
        Integer postId = 1;
        String commentId = "1";
        Comment comment = new Comment(commentId, postId, "Comment to delete", "email@example.com", "Comment text");


        when(postRepository.existsById(postId)).thenReturn(true);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));


        boolean result = commentService.delete(postId, commentId);

        verify(commentRepository, times(1)).deleteById(commentId);

        assertTrue(result);
    }




}
