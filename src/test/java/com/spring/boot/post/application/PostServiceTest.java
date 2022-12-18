package com.spring.boot.post.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import com.spring.boot.common.config.InfrastructureTestConfiguration;
import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.member.domain.member.Member;
import com.spring.boot.member.domain.member.MemberRepository;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.PostRepository;
import com.spring.boot.post.domain.tag.PostTag;
import com.spring.boot.post.presentaion.dto.PostCreateRequest;
import com.spring.boot.util.DatabaseCleanUp;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@ActiveProfiles("test")
@Import(InfrastructureTestConfiguration.class)
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
  @DisplayName("포스트를 생성할 수 있다.")
  void 포스트_생성() {
    // given
    Member member = createDummyMember();
    PostCreateRequest postCreateRequest = new PostCreateRequest("title", "body", TAG_NAMES);

    // when
    Post createdPost = postService.createPost(member.getId(), postCreateRequest, IMAGES);

    // then
    assertNotNull(createdPost);
    assertThat(createdPost.getContent(), is(postCreateRequest.getBody()));
    assertThat(createdPost.getTitle(), is(postCreateRequest.getTitle()));
    assertThat(createdPost.getPostTags().size(), is(postCreateRequest.getTagNames().size()));
  }

  @Test
  @DisplayName("글 작성자는 포스트를 지울 수 있다")
  void 포스트_삭제() {
    // given
    Member member = createDummyMember();
    PostCreateRequest postCreateRequest = new PostCreateRequest("title", "body", TAG_NAMES);
    Post createdPost = postService.createPost(member.getId(), postCreateRequest, IMAGES);

    // when
    postService.deletePost(member.getId(), createdPost.getId());
  }

  @Test
  @DisplayName("글 작성자가 아니면 포스트를 지울 수 없다")
  void 포스트_삭제_실패() {
    // given
    Member writer = createDummyMember();
    Member member = createDummyMember();
    PostCreateRequest postCreateRequest = new PostCreateRequest("title", "body", TAG_NAMES);
    Post createdPost = postService.createPost(writer.getId(), postCreateRequest, IMAGES);

    // when
    assertThrows(AccessDeniedException.class, () -> {
      postService.deletePost(member.getId(), createdPost.getId());
    });
  }

  @Test
  @DisplayName("글 제목, 내용, 태그들을 수정할 수 있다.")
  void 포스트_수정() {
    // given
    String NEW_TITLE = "new title";
    String NEW_CONTENT = "new content";
    List<String> NEW_TAG_NAMES = Arrays.asList("tag1", "newa", "newb");

    Member writer = createDummyMember();
    PostCreateRequest postCreateRequest = new PostCreateRequest("title", "body", TAG_NAMES);
    Post createdPost = postService.createPost(writer.getId(), postCreateRequest, IMAGES);

    // when
    postService.updatePost(writer.getId(), createdPost.getId(), NEW_TITLE, NEW_CONTENT,
        NEW_TAG_NAMES);
    Post updated = postService.getPostByPostId(createdPost.getId());

    // then
    assertThat(updated.getTitle(), is(NEW_TITLE));
    assertThat(updated.getContent(), is(NEW_CONTENT));
    assertTags(updated, NEW_TAG_NAMES);

  }

  @Test
  @DisplayName("잘못된 유저 아이디일 경우 생성할 수 없다.")
  void 포스트_생성_실패_잘못된_유저아이디() {
    PostCreateRequest postCreateRequest = new PostCreateRequest("title", "body", Collections.emptyList());

    assertThrows(NotFoundException.class, () -> {
      postService.createPost(-1L, postCreateRequest, Collections.emptyList());
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

}