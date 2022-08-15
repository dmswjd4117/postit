package com.spring.boot.service;

import com.spring.boot.post.application.PostService;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.PostTag;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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


    String title;
    String body;
    Long writerId;
    List<String> tagNames;

    @BeforeAll
    void before(){
        title = "title";
        body = "body";
        writerId = 1L;
        tagNames = Arrays.asList("tag1", "tag2", "tag2", "tag1", "tag2", "tag3", "tag4", "tag5");
    }

    // https://codechacha.com/ko/hamcrest-collections-matcher/
    @Test
    @Order(1)
    void 포스트_작성(){
        Post post = postService.createPost(title, body, writerId, tagNames, Collections.emptyList());

        assertThat(post, is(CoreMatchers.notNullValue()));
        assertThat(post.getId(), is(CoreMatchers.notNullValue()));
        assertThat(post.getBody(), is(body));
        assertThat(post.getPostTags().size(), is(5));

        assertThat(post.getPostTags()
                .stream()
                .map(PostTag::getTagName)
                .collect(Collectors.toList()), containsInAnyOrder("tag1", "tag2", "tag3", "tag4", "tag5"));

        log.info("post: {}", post);
    }

    @Test
    @Order(2)
    void 포스트_아이디로_조회(){
        Post post = postService.getPostByPostId(4L);

        assertThat(post, is(notNullValue()));
        assertThat(post.getId(), is(CoreMatchers.notNullValue()));
        assertThat(post.getBody(), is(body));
        assertThat(post.getPostTags().size(), is(5));

        assertThat(post.getPostTags()
                .stream()
                .map(PostTag::getTagName)
                .collect(Collectors.toList()), containsInAnyOrder("tag1", "tag2", "tag3", "tag4", "tag5"));
    }

    @Test
    @Order(2)
    void 포스트_멤버아이디로_조회(){
        Post post = postService.getPostByMemberId(writerId).get(0);

        assertThat(post, is(notNullValue()));
        assertThat(post.getId(), is(CoreMatchers.notNullValue()));
        assertThat(post.getBody(), is(body));
        assertThat(post.getPostTags().size(), is(5));

        assertThat(post.getPostTags()
                .stream()
                .map(PostTag::getTagName)
                .collect(Collectors.toList()), containsInAnyOrder("tag1", "tag2", "tag3", "tag4", "tag5"));
    }


}