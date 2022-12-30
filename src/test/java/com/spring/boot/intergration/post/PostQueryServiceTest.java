package com.spring.boot.intergration.post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

import com.spring.boot.connection.domain.Connection;
import com.spring.boot.connection.domain.ConnectionRepository;
import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.domain.Post;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

public class PostQueryServiceTest extends IntegrationTest {

  @Autowired
  private PostQueryService postQueryService;
  @Autowired
  private ConnectionRepository connectionRepository;

  @Test
  @DisplayName("팔로잉하고 있는 멤버들의 게시물들을 조회한다")
  void 팔로잉들의_게시물_조회() {
    // given
    Member member = saveMember("member@naver.com");

    for (int i = 0; i < 4; i++) {
      Member targetMember = saveMember(i + "email@naver.com");

      connectionRepository.save(new Connection(member, targetMember));
      for (int j = 0; j < 2; j++) {
        savePost(targetMember);
      }
    }

    // when
    Pageable pageable = Pageable.ofSize(2);
    List<PostInfoDto> findPosts = postQueryService.getAllFollowingsPost(member.getId(),
        pageable);

    // then
    assertThat(findPosts.size(), is(8));
  }

  @Test
  @DisplayName("포스트 아이디로 포스트를 조회할 수 있다.")
  void 게시물_게시물아이디로_조회() {
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
//    assertThat(extractTagNames(post.getTags()), is(extractTagNames(origin.getTags())));
  }

  @Test
  @DisplayName("멤버 아이디로 글들을 조회한다")
  void 게시물_멤버아이디로_조회() {
    int DUMMY_POST_CNT = 3;

    // given
    Member member = saveMember("email@naver.com");

    for (int i = 0; i < DUMMY_POST_CNT; i++) {
      savePost(member);
    }

    // when
    List<PostInfoDto> findPosts = postQueryService.getPostByMemberId(
        member.getId());

    // then
    assertThat(findPosts.size(), is(DUMMY_POST_CNT));
  }

  @Test
  @DisplayName("모든 게시물 조회한다")
  void 모든_게시물_조회() {
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
    List<PostInfoDto> posts = postQueryService.getAllPost();

    // then
    assertThat(posts.size(), is(5));
  }


}
