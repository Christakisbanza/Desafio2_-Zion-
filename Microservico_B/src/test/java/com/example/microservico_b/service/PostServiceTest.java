package com.example.microservico_b.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import static com.example.microservico_b.common.PostConstants.POST;

@SpringBootTest(classes = PostService.class)
public class PostServiceTest {
    private PostService postService;


    @Test
    public void deletePost_WithValidId_DeletesPost(){
        postService.deletePost(POST.getId());


    }
}
