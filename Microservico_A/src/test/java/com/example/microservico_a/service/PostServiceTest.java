package com.example.microservico_a.service;

import com.example.microservico_a.client.JsonPlaceholderClient;
import com.example.microservico_a.entities.Post;
import com.example.microservico_a.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService; // Mockito injeta os mocks aqui automaticamente

    @Mock
    private JsonPlaceholderClient jsonPlaceholderClient;

    @Mock
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks e @InjectMocks
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



}