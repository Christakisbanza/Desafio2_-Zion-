package com.example.microservico_b.service;

import com.example.microservico_b.client.JsonPlaceholderClient;
import com.example.microservico_b.exception.PostNotFoundException;
import com.example.microservico_b.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static com.example.microservico_b.common.PostConstants.POST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = PostService.class)
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {


    @MockitoBean
    private PostService postService;


    @Test
    public void deletePost_WithValidId_DoesNotThrowAnyException() {
        assertThatCode(() -> postService.delete(POST.getId())).doesNotThrowAnyException();
    }

    @Test
    public void deletePost_WithInvalidId_ThrowsException() {
        doThrow(new PostNotFoundException("Post with id 9999 not found")).when(postService).delete(9999);

        assertThatThrownBy(() -> postService.delete(9999))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessage("Post with id 9999 not found");
    }

}
