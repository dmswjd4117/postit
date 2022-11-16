package com.spring.boot.post.application;

import com.spring.boot.member.domain.member.Member;
import com.spring.boot.member.domain.member.MemberRepository;
import com.spring.boot.post.application.dto.PostRequest;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.PostRepository;
import com.spring.boot.post.domain.PostTag;
import com.spring.boot.util.DatabaseCleanUp;
import java.util.Collections;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {

    private final String TITLE = "title";
    private final String BODY = "body";
    private final List<String> TAG_NAMES = Arrays.asList("tag1", "tag2");
    private final List<MultipartFile> IMAGES = Arrays.asList(
        new MockMultipartFile("test", "test.jpg", IMAGE_PNG_VALUE, "test".getBytes()),
        new MockMultipartFile("test2", "test2.jpg", IMAGE_PNG_VALUE, "test2".getBytes())
    );

    @Autowired
    DatabaseCleanUp databaseCleanUp;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void cleanUp(){
        databaseCleanUp.truncateAllEntity();
    }

    Member createDummyMember(){
        return memberRepository.save(new Member("test@gmail.com", "pass", "name"));
    }

    PostRequest createPostRequestDto(){
        return new PostRequest(TITLE, BODY, TAG_NAMES);
    }

    Post createDummyPost(Long memberId){
        PostRequest postRequest = createPostRequestDto();
        return postService.createPost(memberId, postRequest, IMAGES);
    }

    @Test
    @DisplayName("포스트를 생성할 수 있다.")
    void 포스트_생성(){
        Member member = createDummyMember();
        PostRequest postRequest = new PostRequest("title", "body", TAG_NAMES);

        Post createdPost = postService.createPost(member.getId(), postRequest, IMAGES);

        assertNotNull(createdPost);
        assertThat(createdPost.getBody(), is(postRequest.getBody()));
        assertThat(createdPost.getTitle(), is(postRequest.getTitle()));
        assertThat(createdPost.getPostTags().size(), is(postRequest.getPostTags().size()));
    }

    @Test
    @DisplayName("잘못된 유저 아이디일 경우 생성할 수 없다.")
    void 포스트_생성_실패_잘못된_유저아이디(){
        PostRequest postRequest = new PostRequest("title", "body", Collections.emptyList());

        assertThrows(IllegalArgumentException.class, ()->{
            postService.createPost(-1L, postRequest, Collections.emptyList());
        });
    }

    @Test
    @DisplayName("포스트 아이디로 포스트를 조회할 수 있다.")
    void 포스트_아이디_조회(){
        // given
        Member member = createDummyMember();
        Post post = createDummyPost(member.getId());

        // when
        Post findPost = postService.getPostByPostId(post.getId());

        // then
        assertThat(findPost, is(notNullValue()));

        assertThat(findPost.getBody(), is(BODY));
        assertThat(findPost.getMember().getName(), is(member.getName()));
        assertThat(findPost.getImages().size(), is(IMAGES.size()));

        assertAll(() -> {
            assertThat(findPost.getPostTags().size(), is(TAG_NAMES.size()));
            assertThat(findPost.getPostTags()
                .stream()
                .map(PostTag::getTagName)
                .collect(Collectors.toList()), containsInAnyOrder("tag1", "tag2"));
        });
    }


    @Test
    @DisplayName("포스트 아이디로 조회한다")
    void 포스트_멤버_아이디로_조회(){
        int DUMMY_POST_CNT = 3;

        // given
        Member member = createDummyMember();
        for(int i=0; i<DUMMY_POST_CNT; i++){
            createDummyPost(member.getId());
        }

        // when
        List<Post> posts = postService.getPostByMemberId(member.getId());


        // then
        assertThat(posts.size(), is(DUMMY_POST_CNT));
        for(int index=0; index<DUMMY_POST_CNT; index++){
            Post findPost = posts.get(index);
            assertThat(findPost, is(notNullValue()));

            assertThat(findPost.getBody(), is(BODY));
            assertThat(findPost.getMember().getName(), is(member.getName()));
            assertThat(findPost.getImages().size(), is(IMAGES.size()));

            assertAll(() -> {
                assertThat(findPost.getPostTags().size(), is(TAG_NAMES.size()));
                assertThat(findPost.getPostTags()
                    .stream()
                    .map(PostTag::getTagName)
                    .collect(Collectors.toList()), containsInAnyOrder("tag1", "tag2"));
            });
        }
    }

}