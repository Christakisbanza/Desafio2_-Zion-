package com.example.microservico_b.service;

import com.example.microservico_b.client.JsonPlaceholderClient;
import com.example.microservico_b.model.entities.Post;
import com.example.microservico_b.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static com.example.microservico_b.common.PostConstants.POST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PostService.class)
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private JsonPlaceholderClient jsonPlaceholderClient;

    @MockitoBean
    private PostService postService;

    @Mock
    private PostRepository postRepository;



    @Test
    public void deletePost_WithValidId_DoesNotThrowAnyException() {
        assertThatCode(() -> postService.deletePost(POST.getId())).doesNotThrowAnyException();
    }

    @Test
    public void deletePost_WithInvalidId_ThrowsException() {
        doThrow(new RuntimeException()).when(postService).deletePost(99);

        assertThatThrownBy(() -> postService.deletePost(99)).isInstanceOf(RuntimeException.class);
    }

}
