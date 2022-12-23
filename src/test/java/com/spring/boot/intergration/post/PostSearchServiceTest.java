package com.spring.boot.intergration.post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import com.spring.boot.common.config.InfrastructureTestConfiguration;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.MemberRepository;
import com.spring.boot.post.application.PostSearchService;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.PostRepository;
import com.spring.boot.post.domain.tag.PostTag;
import com.spring.boot.post.presentaion.dto.PostCreateRequest;
import com.spring.boot.common.DatabaseCleanUp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@ActiveProfiles("test")
@Import(InfrastructureTestConfiguration.class)
class PostSearchServiceTest {

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
  @Autowired
  private PostSearchService postSearchService;

  @BeforeEach
  void cleanUp() {
    databaseCleanUp.clear();
  }

  Member createDummyMember() {
    return memberRepository.save(new Member(RandomUtils.nextLong() + "@gmail.com", "pass", "name"));
  }

  PostCreateRequest createPostRequestDto() {
    return new PostCreateRequest(TITLE, BODY, TAG_NAMES);
  }

  Post createDummyPost(Long memberId) {
    PostCreateRequest postCreateRequest = createPostRequestDto();
    return postService.createPost(memberId, postCreateRequest, IMAGES);
  }

  private void assertTags(Post post, List<String> tagNames) {
    assertAll(() -> {
      assertThat(post.getPostTags().size(), is(tagNames.size()));
      assertThat(post.getPostTags()
          .stream()
          .map(PostTag::getTagName)
          .collect(Collectors.toSet()), is(equalTo(new HashSet<>(tagNames))));
    });
  }

  @Test
  @DisplayName("포스트 아이디로 포스트를 조회할 수 있다.")
  void 포스트_포스트_아이디로_조회() {
    // given
    Member member = createDummyMember();
    Post post = createDummyPost(member.getId());

    // when
    Post findPost = postService.getPostByPostId(post.getId());

    // then
    assertThat(findPost, is(notNullValue()));

    assertThat(findPost.getContent(), is(BODY));
    assertThat(findPost.getWriter().getName(), is(member.getName()));
    assertThat(findPost.getImages().size(), is(IMAGES.size()));
    assertTags(findPost, TAG_NAMES);
  }


  @Test
  @DisplayName("멤버 아이디로 글들을 조회한다")
  void 포스트_멤버_아이디로_조회() {
    int DUMMY_POST_CNT = 3;

    // given
    Member member = createDummyMember();
    for (int i = 0; i < DUMMY_POST_CNT; i++) {
      createDummyPost(member.getId());
    }

    // when
    List<Post> posts = postService.getPostByMemberId(member.getId());

    // then
    assertThat(posts.size(), is(DUMMY_POST_CNT));
    for (int index = 0; index < DUMMY_POST_CNT; index++) {
      Post findPost = posts.get(index);
      assertThat(findPost, is(notNullValue()));

      assertThat(findPost.getContent(), is(BODY));
      assertThat(findPost.getWriter().getName(), is(member.getName()));
      assertThat(findPost.getImages().size(), is(IMAGES.size()));
      assertTags(findPost, TAG_NAMES);
    }
  }

  @Test
  @DisplayName("모든 게시물 조회한다")
  void search() {
    // given
    Member member = createDummyMember();
    for (int i = 0; i < 2; i++) {
      createDummyPost(member.getId());
    }

    Member member2 = createDummyMember();
    for (int i = 0; i < 3; i++) {
      createDummyPost(member2.getId());
    }

//    // when
//    List<Post> posts = postSearchService.getAllPost();
//    for (Post post: posts) {
//      System.out.println(post);
//      System.out.println(post.getWriter());
//      System.out.println(post.getImages());
//      System.out.println(post.getPostTags());
//    }
  }
}