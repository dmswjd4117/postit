package com.spring.boot.intergration.post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.domain.Post;
import com.spring.boot.member.domain.Member;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

public class PostQueryServiceTest extends IntegrationTest {

  @Autowired
  private PostQueryService postQueryService;

  @Test
  @DisplayName("포스팅 아이디로 조회성공")
  void 게시물아이디로_조회() {
    // given
    Member member = saveMember("email@naver.com");
    Post post = savePost(member);

    // when
    PostInfoDto findPost = postQueryService.getPostByPostId(post.getId());

    // then
    assertThat(post, is(notNullValue()));
    assertThat(post.getContent(), is(findPost.getContent()));
    assertThat(post.getWriter().getId(), is(findPost.getWriter().getId()));
    assertThat(post.getPostImages().size(), is(findPost.getImages().size()));
    assertThat(post.getPostTags().size(), is(findPost.getTags().size()));
  }

  @Nested
  @DisplayName("멤버 아이디로 조회할 때")
  class 멤버_아이디로_조회 {

    @Test
    @DisplayName("조회 성공")
    void 게시물_조회() {
      int DUMMY_POST_CNT = 3;

      // given
      Member member = saveMember("email@naver.com");

      for (int i = 0; i < DUMMY_POST_CNT; i++) {
        savePost(member);
      }

      // when
      List<PostInfoDto> findPosts = postQueryService.getPostByMemberId(
          member.getId(), PageRequest.ofSize(DUMMY_POST_CNT));

      // then
      assertThat(findPosts.size(), is(DUMMY_POST_CNT));
    }

    @Test
    @DisplayName("글 2개 조회 성공")
    void 게시물_2개_조회() {
      int DUMMY_POST_CNT = 3;

      // given
      Member member = saveMember("email@naver.com");

      for (int i = 0; i < DUMMY_POST_CNT; i++) {
        savePost(member);
      }

      // when
      List<PostInfoDto> findPosts = postQueryService.getPostByMemberId(
          member.getId(), PageRequest.ofSize(2));

      // then
      assertThat(findPosts.size(), is(2));
    }
  }


  @Nested
  @DisplayName("팔로잉하고 있는 멤버들의 게시물 조회할 때")
  class 팔로잉멤버들의_게시물_조회 {

    @Test
    @DisplayName("조회 성공")
    void 게시물_조회() {
      // given
      Member member = saveMember("email@naver.com");

      for (int i = 0; i < 2; i++) {
        savePost(member);
      }

      Member member2 = saveMember("email2@naver.com");
      for (int i = 0; i < 3; i++) {
        savePost(member2);
      }

      // when
      List<PostInfoDto> posts = postQueryService.getAllPost(PageRequest.ofSize(6));

      // then
      assertThat(posts.size(), is(5));
    }

    @Test
    @DisplayName("글 2개 조회 성공")
    void 게시물_2개_조회() {
      // given
      Member member = saveMember("email@naver.com");

      for (int i = 0; i < 2; i++) {
        savePost(member);
      }

      Member member2 = saveMember("email2@naver.com");
      for (int i = 0; i < 3; i++) {
        savePost(member2);
      }

      // when
      List<PostInfoDto> posts = postQueryService.getAllPost(PageRequest.ofSize(2));

      // then
      assertThat(posts.size(), is(2));
    }
  }


}
