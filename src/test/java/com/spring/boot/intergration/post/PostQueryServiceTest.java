package com.spring.boot.intergration.post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

import com.spring.boot.common.mock.MockPost;
import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.presentation.dto.response.HomeFeedPostResponse;
import com.spring.boot.post.application.dto.response.PostResponseDto;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

public class PostQueryServiceTest extends IntegrationTest {

  @Autowired
  private PostQueryService postQueryService;
  @Autowired
  private PostRepository postRepository;

  @Test
  @DisplayName("포스팅 아이디로 조회")
  void 게시물아이디로_조회() {
    // given
    Member member = saveMember("email@naver.com");
    Post post = postRepository.save(MockPost.create(member));

    // when
    PostResponseDto findPost = postQueryService.getPostByPostId(post.getId(), member.getId());

    // then
    assertThat(post, is(notNullValue()));
    assertThat(post.getContent(), is(findPost.getContent()));
    assertThat(post.getWriter().getId(), is(findPost.getWriter().getId()));
    assertThat(post.getPostImages().size(), is(findPost.getImages().size()));
    assertThat(post.getPostTags().size(), is(findPost.getTags().size()));
  }

  @Nested
  @DisplayName("홈피드 조회한다")
  class 홈_피드 {

    @Autowired
    private ConnectionService connectionService;
    private Member reader;
    private List<Post> posts;

    @BeforeEach
    void before() {
      // member
      this.reader = saveMember("reader@gmail.com");
      Member following = saveMember("m1@gmail.com");
      Member following2 = saveMember("m2@gmail.com");

      connectionService.follow(reader.getId(), following.getId());
      connectionService.follow(reader.getId(), following2.getId());

      // post
      this.posts = new ArrayList<>();
      this.posts.add(postRepository.save(MockPost.create(reader)));

      for (int i = 0; i < 3; i++) {
        posts.add(postRepository.save(MockPost.create(following)));
      }

      for (int i = 0; i < 3; i++) {
        posts.add(postRepository.save(MockPost.create(following2)));
      }
    }

    @Test
    @DisplayName("post id가 null 이면 첫페이지 조회한다.")
    void 홈피드_조회() {
      // given
      int pageSize = 10;

      // when
      List<PostResponseDto> homeFeedPost = postQueryService.getHomeFeedPost(reader.getId(),
          pageSize, null);

      // then
      assertThat(homeFeedPost.size(), is(posts.size()));
    }

    @Test
    @DisplayName("post id 5보다 작은 4개의 최신 게시물조회")
    void 홈피드_조회_페이징() {

      List<PostResponseDto> homeFeedPost = postQueryService.getHomeFeedPost(reader.getId(),
          4, 5L);

      assertThat(homeFeedPost.size(), is(4));

      List<Long> ids = homeFeedPost.stream()
          .map(PostResponseDto::getId)
          .collect(Collectors.toList());

      assertThat(ids, containsInAnyOrder(4L, 3L, 2L, 1L));
    }
  }

  @Nested
  @DisplayName("멤버 아이디로 조회한다")
  class 멤버_아이디로_조회 {

    private Member member;
    private List<Post> posts;
    private int DUMMY_POST_CNT = 5;

    @BeforeEach
    void before() {
      member = saveMember("email@naver.com");
      posts = new ArrayList<>();
      for (int i = 0; i < DUMMY_POST_CNT; i++) {
        Post post = new Post.Builder("title", "content", member)
            .build();
        posts.add(postRepository.save(post));
      }
    }

    @Test
    @DisplayName("페이지0, 오프셋3")
    void 게시물_조회() {
      // given
      PageRequest pageRequest = PageRequest.of(0, 3);

      // when
      List<PostResponseDto> findPosts = postQueryService.getPostByWriterId(
          member.getId(),
          pageRequest,
          null);

      // then
      assertThat(findPosts.size(), is(3));
    }

    @Test
    @DisplayName("페이지1, 오프셋3")
    void 게시물_조회2() {
      // given
      PageRequest pageRequest = PageRequest.of(1, 3);

      // when
      List<PostResponseDto> findPosts = postQueryService.getPostByWriterId(member.getId(),
          PageRequest.ofSize(2), null);

      // then
      assertThat(findPosts.size(), is(2));
    }
  }


  @Nested
  @DisplayName("팔로잉하고 있는 멤버들의 게시물 조회할 때")
  class 팔로잉멤버들의_게시물_조회 {

    @Autowired
    private ConnectionService connectionService;

    private Member member;
    private Member member2;
    private Member reader;
    private List<Post> posts;

    @BeforeEach
    void before() {
      member = saveMember("email@naver.com");
      member2 = saveMember("email2@naver.com");
      reader = saveMember("reader@gmail.com");

      connectionService.follow(reader.getId(), member.getId());
      connectionService.follow(reader.getId(), member2.getId());

      posts = new ArrayList<>();
      for (int i = 0; i < 2; i++) {
        posts.add(postRepository.save(MockPost.create(member)));
      }

      for (int i = 0; i < 3; i++) {
        posts.add(postRepository.save(MockPost.create(member2)));
      }
    }


    @Test
    @DisplayName("페이지0, 사이즈5일때")
    void 게시물_조회() {
      // given
      PageRequest pageRequest = PageRequest.of(0, 5);

      // when
      List<PostResponseDto> posts = postQueryService.getFollowingsPost(reader.getId(), pageRequest);

      // then
      assertThat(posts.size(), is(5));
    }

    @Test
    @DisplayName("페이지1, 사이즈3")
    void 게시물_조회2() {
      // given
      PageRequest pageRequest = PageRequest.of(1, 3);

      // when
      List<PostResponseDto> posts = postQueryService.getFollowingsPost(reader.getId(), pageRequest);

      // then
      assertThat(posts.size(), is(2));
    }
  }


}
