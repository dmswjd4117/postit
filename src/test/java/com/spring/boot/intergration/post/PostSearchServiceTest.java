package com.spring.boot.intergration.post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.member.application.dto.MemberInfoDto;
import com.spring.boot.post.application.dto.PostInfoDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class PostSearchServiceTest extends IntegrationTest {

  @Test
  @DisplayName("포스트 아이디로 포스트를 조회할 수 있다.")
  void 포스트_포스트_아이디로_조회() {
    // given
    MemberInfoDto member = saveMember();
    PostInfoDto post = savePost(member.getId());

    // when
    PostInfoDto findPost = postSearchService.getPostByPostId(post.getId());

    // then
    assertThat(findPost, is(notNullValue()));

    assertThat(findPost.getContent(), is(post.getContent()));
    assertThat(findPost.getWriter().getName(), is(member.getName()));
    assertThat(findPost.getImages().size(), is(post.getImages().size()));
    assertThat(extractTagNames(findPost.getTags()),
        is(extractTagNames(post.getTags())));
  }


  @Test
  @DisplayName("멤버 아이디로 글들을 조회한다")
  void 포스트_멤버_아이디로_조회() {
    int DUMMY_POST_CNT = 3;

    // given
    MemberInfoDto member = saveMember();
    for (int i = 0; i < DUMMY_POST_CNT; i++) {
      savePost(member.getId());
    }

    // when
    List<PostInfoDto> postInfoDtos = postSearchService.getPostByMemberId(
        member.getId());

    // then
    assertThat(postInfoDtos.size(), is(DUMMY_POST_CNT));
    for (int index = 0; index < DUMMY_POST_CNT; index++) {
      com.spring.boot.post.application.dto.PostInfoDto findPostInfoDto = postInfoDtos.get(index);
      assertThat(findPostInfoDto, is(notNullValue()));

      assertThat(findPostInfoDto.getContent(), is(CONTENT));
      assertThat(findPostInfoDto.getWriter().getName(), is(member.getName()));
      assertThat(findPostInfoDto.getImages().size(), is(IMAGES.size()));
      assertThat(findPostInfoDto.getTags().size(), is(TAG_NAMES.size()));
    }
  }

  @Test
  @DisplayName("모든 게시물 조회한다")
  void search() {
    // given
    MemberInfoDto member = saveMember();
    for (int i = 0; i < 2; i++) {
      savePost(member.getId());
    }

    MemberInfoDto member2 = saveMember();
    for (int i = 0; i < 3; i++) {
      savePost(member2.getId());
    }

//    // when
//    List<Post> posts = postSearchService.getAllPost();
//    for (Post post: posts) {
//      System.out.println(post);
//      System.out.println(post.getWriter());
//      System.out.println(post.getImages());
//      System.out.println(post.getTags());
//    }
  }
}