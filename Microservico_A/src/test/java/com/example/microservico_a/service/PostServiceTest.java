package com.example.microservico_a.service;

import com.example.microservico_a.client.JsonPlaceholderClient;
import com.example.microservico_a.entities.Post;
import com.example.microservico_a.exception.EntityNotFoundException;
import com.example.microservico_a.exception.PostNotFoundException;
import com.example.microservico_a.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.microservico_a.common.PostConstants.POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private JsonPlaceholderClient jsonPlaceholderClient;

    @Mock
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        post = new Post(1, 1, "titlePost", "bodyPost");
    }

    @Test
    public void testGetAllPosts() {
        when(jsonPlaceholderClient.getPosts()).thenReturn(Collections.singletonList(post));

        List<Post> posts = postService.getAllPosts();

        assertThat(posts).hasSize(1);
        assertThat(posts.get(0)).isEqualTo(post);
        verify(jsonPlaceholderClient, times(1)).getPosts();
    }
    @Test
    public void testGetAllPosts_WithEmptyList() {

        when(jsonPlaceholderClient.getPosts()).thenReturn(Collections.emptyList());

        List<Post> result = postService.getAllPosts();

        assertTrue(result.isEmpty());
        verify(jsonPlaceholderClient, times(1)).getPosts();
    }

    @Test
    public void getPostById_WithValidId_DoesNotThrowAnyException() {
        when(jsonPlaceholderClient.findById(POST.getId())).thenReturn(new ResponseEntity<>(POST, HttpStatus.OK));

       Post post = postService.findById(POST.getId());

        assertThat(post).isNotNull();
        assertThat(post).isEqualTo(POST);
        verify(jsonPlaceholderClient, times(1)).findById(POST.getId());
    }

    @Test
    public void getPostById_WithInvalidId_ReturnsException() {
        when(jsonPlaceholderClient.findById(POST.getId())).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        assertThrows(PostNotFoundException.class, () -> postService.findById(POST.getId()));
    }




}