package com.example.microservico_b.service;

import com.example.microservico_b.client.JsonPlaceholderClient;
import com.example.microservico_b.exception.PostNotFoundException;
import com.example.microservico_b.model.entities.Post;
import com.example.microservico_b.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostService2Test {

    @InjectMocks
    private PostService postService;

    @Mock
    private JsonPlaceholderClient jsonPlaceholderClient;

    @Mock
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    public void setUp() {
        post = new Post(1, 1, "titlePost", "bodyPost");
    }

    @Test
    public void testFindAll() {
        List<Post> posts = Collections.singletonList(post);
        when(postRepository.findAll()).thenReturn(posts);

        List<Post> result = postService.findAll();

        assertEquals(1, result.size());
        assertEquals(post, result.get(0));
        verify(postRepository, times(1)).findAll();
    }

    @Test
    public void testFindAll_EmptyList() {
        when(postRepository.findAll()).thenReturn(Collections.emptyList());

        List<Post> result = postService.findAll();

        assertTrue(result.isEmpty());

        verify(postRepository, times(1)).findAll();
    }

    @Test
    public void testSyncData() {
        List<Post> posts = Arrays.asList(
                new Post(2, 1, "titlePost2", "bodyPost2"),
                new Post(3, 1, "titlePost3", "bodyPost3")
        );
        when(postRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(post));
        when(jsonPlaceholderClient.getPosts()).thenReturn(posts);

        List<Post> result = postService.syncData();

        assertEquals(2, result.size());
        assertEquals("titlePost2", result.get(0).getTitle());
        assertEquals("titlePost3", result.get(1).getTitle());

        verify(postRepository, times(1)).saveAll(posts);
    }

    @Test
    public void testSave() {
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
    public void testGetById() {
        when(postRepository.findById(1)).thenReturn(Optional.of(post));

        Post result = postService.getById(1);

        assertNotNull(result);
        assertEquals("titlePost", result.getTitle());
        assertEquals("bodyPost", result.getBody());

        verify(postRepository, times(1)).findById(1);
    }

    @Test
    public void testGetByIdNotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.getById(1));
    }

    @Test
    public void testUpdate() {
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post updatedPost = new Post(1, 2, "updatedTitlePost", "updatedBodyPost");

        Post result = postService.update(updatedPost);

        assertNotNull(result);
        assertEquals(2, result.getUserId());
        assertEquals("updatedTitlePost", result.getTitle());
        assertEquals("updatedBodyPost", result.getBody());

        verify(postRepository, times(1)).save(any(Post.class));
    }
}
