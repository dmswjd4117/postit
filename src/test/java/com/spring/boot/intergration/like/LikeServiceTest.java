package com.spring.boot.intergration.like;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.like.application.LikeService;
import com.spring.boot.like.application.dto.LikeDto;
import com.spring.boot.like.domain.Like;
import com.spring.boot.like.domain.LikeRepository;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LikeServiceTest extends IntegrationTest {

  @Autowired
  private LikeService likeService;
  @Autowired
  private LikeRepository likeRepository;

  @Nested
  @DisplayName("좋아요가 이미 눌러져 있을 때")
  class 좋아요_눌러져있을때{
    @Test
    @DisplayName("한번 누르면 좋아요 최종 개수 0개")
    public void 한번_누름() {
      // given
      Member member = saveMember("member@email.com");
      Member writer = saveMember("writer@email.com");
      Post post = savePost(writer);
      likeService.like(member.getId(), post.getId());

      // when
      likeService.unlike(member.getId(), post.getId());

      // then
      List<LikeDto> likes = likeService.getLikes(post.getId());
      assertThat(likes.size(), is(0));
    }

    @Test
    @DisplayName("동시에 좋아요 누를 경우 좋아요 최종 개수 0개")
    public void 동시에_누름() throws InterruptedException {
      // given
      Member member = saveMember("member@email.com");
      Member writer = saveMember("를riter@email.com");
      Post post = savePost(writer);
      likeService.like(member.getId(), post.getId());

      // when
      ExecutorService executorService = Executors.newFixedThreadPool(30);
      CountDownLatch latch = new CountDownLatch(100);
      for (int i = 0; i < 100; i++) {
        executorService.submit(() -> {
          try {
            likeService.unlike(member.getId(), post.getId());
          } finally {
            latch.countDown();
          }
        });
      }

      // then
      latch.await();
      List<LikeDto> likes = likeService.getLikes(post.getId());
      assertThat(likes.size(), is(0));
    }
  }

  @Nested
  @DisplayName("좋아요가 눌러져 있지 않을 때")
  class 좋아요가_눌러져있지_않을때 {

    @Test
    @DisplayName("한번 누르면 좋아요 최종 개수 1개")
    public void 한번_좋아요() {
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
    @DisplayName("동시에 100번 누르면 좋아요 최종 개수 1개")
    public void 동시에_좋아요() throws InterruptedException {
      // given
      Member member = saveMember("member@email.com");
      Member writer = saveMember("writer@email.com");
      Post post = savePost(writer);

      // when
      ExecutorService executorService = Executors.newFixedThreadPool(30);
      CountDownLatch latch = new CountDownLatch(100);
      for (int i = 0; i < 100; i++) {
        executorService.submit(() -> {
          try {
            likeService.like(member.getId(), post.getId());
          } finally {
            latch.countDown();
          }
        });
      }

      // then
      latch.await();
      List<LikeDto> likes = likeService.getLikes(post.getId());
      assertThat(likes.size(), is(1));
    }

  }

}









