package com.example.microservico_a.service;

import com.example.microservico_a.client.JsonPlaceholderClient;
import com.example.microservico_a.controller.dto.PostCreateDto;
import com.example.microservico_a.controller.exception.ErrorMessage;
import com.example.microservico_a.entities.Post;
import com.example.microservico_a.exception.EntityNotFoundException;
import com.example.microservico_a.exception.PostNotFoundException;
import com.example.microservico_a.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.microservico_a.common.PostConstants.POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private JsonPlaceholderClient jsonPlaceholderClient;

    @Mock
    private PostRepository postRepository;

    private Post post;

    @Autowired
    WebTestClient testClient;

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

    @Test
    public void testSave_WithSuccess() {
        when(postRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(new Post(1, 1, "existingTitlePost", "existingBodyPost")));
        when(postRepository.save(any(Post.class))).thenReturn(new Post(2, 2, "newTitlePost", "newBodyPost"));

        Post newPost = new Post(null, 2, "newTitlePost", "newBodyPost");

        Post result = postService.save(newPost);

        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("newTitlePost", result.getTitle());
        assertEquals("newBodyPost", result.getBody());

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testSave_WithInputInvalid_ReturnError422() {
        ErrorMessage responseDto = testClient
                .post()
                .uri("/api/posts/ms-a")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostCreateDto(null, " "," "))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDto).isNotNull();
        Assertions.assertThat(responseDto.getStatus()).isEqualTo(422);
    }

    @Test
    public void testUpdateById_WithSuccess() {
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post updatedPost = new Post(1, 2, "updatedTitlePost", "updatedBodyPost");

        Post result = postService.updateById(updatedPost.getId(),updatedPost);

        assertNotNull(result);
        assertEquals(2, result.getUserId());
        assertEquals("updatedTitlePost", result.getTitle());
        assertEquals("updatedBodyPost", result.getBody());

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testUpdateById_WithInvalidId_ReturnError400() {
        getPostById_WithInvalidId_ReturnsException();
    }



}