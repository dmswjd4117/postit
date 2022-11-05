package com.spring.boot.post.application;

import com.spring.boot.common.domain.BaseTime;
import com.spring.boot.member.domain.member.Member;
import com.spring.boot.member.domain.member.MemberRepository;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.application.dto.PostRequestDto;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.PostRepository;
import com.spring.boot.post.domain.PostTag;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;


    Member getDummyMember(){
        return memberRepository.save(new Member("test@gmail.com", "pass", "name"));
    }
    PostRequestDto getPostRequestDto(Long memberId){
        String title = "title";
        String body = "body";
        List<String> tagNames = Arrays.asList("tag1", "tag2", "tag2");
        return new PostRequestDto(title, body, memberId, tagNames);
    }

    Post getDummyPost(Member member ){
        PostRequestDto postRequest = getPostRequestDto(member.getId());
        List<MultipartFile> images = Arrays.asList(
            new MockMultipartFile("test", "test.jpg", IMAGE_PNG_VALUE, "test".getBytes()),
            new MockMultipartFile("test2", "test2.jpg", IMAGE_PNG_VALUE, "test2".getBytes())
        );

        return postService.createPost(member.getId(), postRequest, images);
    }
    @Test
    @DisplayName("포스트 아이디로 포스트를 조회할 수 있다.")
    @Transactional
    void 포스트_아이디_조회(){
        // given
        Member member = getDummyMember();
        Post post = getDummyPost(member);

        // when
        Post findPost = postService.getPostByPostId(post.getId());

        // then
        assertAll(()->{
            assertThat(findPost, is(notNullValue()));

            assertThat(findPost.getId(), is(CoreMatchers.notNullValue()));
            assertThat(findPost.getBody(), is(post.getBody()));
            assertThat(findPost.getImages().size(), is(post.getImages().size()));
            assertThat(findPost.getPostTags().size(), is(post.getPostTags().size()));
            assertThat(findPost.getPostTags()
                .stream()
                .map(PostTag::getTagName)
                .collect(Collectors.toList()), containsInAnyOrder("tag1", "tag2"));
        });
    }


    @Test
    @Transactional
    void 포스트_멤버아이디로_조회(){
        // given
        Member member = getDummyMember();
        Post post = getDummyPost(member);

        // then
        List<Post> posts = postService.getPostByMemberId(member.getId());

        // when
        assertAll(()->{
            assertThat(posts.size(), is(1));

            Post findPost = posts.get(0);

            assertThat(findPost.getId(), is(CoreMatchers.notNullValue()));
            assertThat(findPost.getBody(), is(post.getBody()));
            assertThat(findPost.getImages().size(), is(post.getImages().size()));
            assertThat(findPost.getPostTags().size(), is(post.getPostTags().size()));
            assertThat(findPost.getPostTags()
                .stream()
                .map(PostTag::getTagName)
                .collect(Collectors.toList()), containsInAnyOrder("tag1", "tag2"));
        });
    }

}