package com.example.microservico_b.service;

import com.example.microservico_b.client.JsonPlaceholderClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import static com.example.microservico_b.common.PostConstants.POST;

@SpringBootTest(classes = PostService.class)
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private JsonPlaceholderClient jsonPlaceholderClient;


    @InjectMocks
    private PostService postService;



    @Test
    public void deletePost_WithValidId_DoesNotThrowAnyException() {
        postService.deletePost(POST.getId());

        assertThat(POST).isNull();
    }
}
