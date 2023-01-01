package com.spring.boot.intergration.like;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.like.application.LikeService;
import com.spring.boot.like.domain.Like;
import com.spring.boot.like.domain.LikeRepository;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LikeServiceTest extends IntegrationTest {

  @Autowired
  private LikeService likeService;
  @Autowired
  private LikeRepository likeRepository;

  @Test
  @DisplayName("좋아요 누른다")
  public void 좋아요_누름_성공() {
    Member member = saveMember("member@email.com");
    Member writer = saveMember("writer@email.com");
    Post post = savePost(writer);

    likeService.like(member.getId(), post.getId());

    Optional<Like> like = likeRepository.findByMemberAndPost(member, post);
    assertTrue(like.isPresent());
    Like findLike = like.get();
    assertThat(findLike.getMember().getId(), is(member.getId()));
    assertThat(findLike.getPost().getId(), is(post.getId()));
  }

  @Test
  @DisplayName("좋아요를 이미 눌렀을 경우 취소한다")
  public void 좋아요_취소_성공() {
    Member member = saveMember("member@email.com");
    Member writer = saveMember("writer@email.com");
    Post post = savePost(writer);

    likeService.like(member.getId(), post.getId());
    likeService.like(member.getId(), post.getId());

    Optional<Like> like = likeRepository.findByMemberAndPost(member, post);
    assertFalse(like.isPresent());
  }

}









