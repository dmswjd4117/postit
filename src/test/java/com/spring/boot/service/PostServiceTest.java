package com.spring.boot.service;

import com.spring.boot.domain.post.Post;
import com.spring.boot.domain.post.PostTag;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostServiceTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostService postService;

    // https://codechacha.com/ko/hamcrest-collections-matcher/
    @Test
    @Order(1)
    void 포스트_작성(){
        String title = "title";
        String body = "body";
        List<String> tagNames = Arrays.asList("tag1", "tag2", "tag2");
        Post post = postService.createPost(title, body, 1L, tagNames, Collections.emptyList());

        assertThat(post, is(CoreMatchers.notNullValue()));
        assertThat(post.getId(), is(CoreMatchers.notNullValue()));
        assertThat(post.getBody(), is(body));
        assertThat(post.getPostTags().size(), is(2));

        assertThat(post.getPostTags()
                .stream()
                .map(PostTag::getTagName)
                .collect(Collectors.toList()), containsInAnyOrder("tag1", "tag2"));

        log.info("post: {}", post);
    }

}