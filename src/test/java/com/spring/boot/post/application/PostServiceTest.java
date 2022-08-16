package com.spring.boot.post.application;

import com.spring.boot.post.application.PostService;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.PostTag;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostServiceTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostService postService;


    String title;
    String body;
    Long writerId;
    List<String> tagNames;
    List<MultipartFile> images;

    @BeforeAll
    void before(){
        title = "title";
        body = "body";
        writerId = 1L;
        tagNames = Arrays.asList("tag1", "tag2", "tag2");
        images = Arrays.asList(
                new MockMultipartFile("test", "test.jpg", IMAGE_PNG_VALUE, "test".getBytes()),
                new MockMultipartFile("test2", "test2.jpg", IMAGE_PNG_VALUE, "test2".getBytes())
        );
    }

    // https://codechacha.com/ko/hamcrest-collections-matcher/
    @Test
    @Order(1)
    void 포스트_작성(){
        Post post = postService.createPost(title, body, writerId, tagNames, images);
        log.info("post: {}", post);
    }

    @Test
    @Order(2)
    void 포스트_아이디로_조회(){
        Post post = postService.getPostByPostId(4L);

        assertThat(post, is(notNullValue()));
        assertThat(post.getId(), is(CoreMatchers.notNullValue()));
        assertThat(post.getBody(), is(body));
        assertThat(post.getImages().size(), is(images.size()));
        assertThat(post.getPostTags().size(), is(new HashSet<>(tagNames).size()));

        assertThat(post.getPostTags()
                .stream()
                .map(PostTag::getTagName)
                .collect(Collectors.toList()), containsInAnyOrder("tag1", "tag2"));
    }


    @Test
    @Order(3)
    void 포스트_멤버아이디로_조회(){
        Post post = postService.getPostByMemberId(writerId).get(0);

        assertThat(post, is(notNullValue()));
        assertThat(post.getId(), is(CoreMatchers.notNullValue()));
        assertThat(post.getBody(), is(body));
        assertThat(post.getImages().size(), is(images.size()));
        assertThat(post.getPostTags().size(), is(new HashSet<>(tagNames).size()));

        assertThat(post.getPostTags()
                .stream()
                .map(PostTag::getTagName)
                .collect(Collectors.toList()), containsInAnyOrder("tag1", "tag2"));
    }

    @Test
    @Order(3)
    void 포스트_멤버아이디로_조회2(){
        List<Post> posts = postService.getPostByMemberId(2L);
        assertThat(posts.size(), is(3));
    }

}