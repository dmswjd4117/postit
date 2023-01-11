package com.spring.boot.intergration.like;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.boot.common.exception.DuplicatedException;
import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.common.mock.MockPost;
import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.like.application.LikeService;
import com.spring.boot.like.application.dto.LikeMemberDto;
import com.spring.boot.like.domain.Like;
import com.spring.boot.like.domain.LikeRepository;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
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
  @Autowired
  private PostRepository postRepository;

  private void checkLikeCount(Long postId, int count) {
    List<LikeMemberDto> likes = likeService.getLikeMembers(postId);
    assertThat(likes.size(), is(count));

    Post findPost = postRepository.findById(postId).orElseThrow();
    assertThat(findPost.getLikeTotalCount(), is(count));
  }

  @Nested
  @DisplayName("좋아요가 눌러져 있을 때")
  class 좋아요_눌러져있을때 {

    @Test
    @DisplayName("좋아요 요청하면 예외발생& 좋아요 최종 개수 1개")
    public void 중복_좋아요_요청() {
      Member member = saveMember("member@email.com");
      Member writer = saveMember("writer@email.com");
      Post post = MockPost.create(writer);
      likeService.like(member.getId(), post.getId());

      assertThrows(DuplicatedException.class, () -> {
        likeService.like(member.getId(), post.getId());
      });

      checkLikeCount(post.getId(), 1);
    }

    @Test
    @DisplayName("취소 요청하면 좋아요 최종 개수 0개")
    public void 한번_누름() {
      // given
      Member member = saveMember("member@email.com");
      Member writer = saveMember("writer@email.com");
      Post post = MockPost.create(writer);
      likeService.like(member.getId(), post.getId());

      // when
      likeService.unlike(member.getId(), post.getId());

      // then
      checkLikeCount(post.getId(), 0);
    }

    @Test
    @DisplayName("동시에 여러번 취소 요청하면 좋아요 최종 개수 0개")
    public void 동시에_누름() throws InterruptedException {
      // given
      Member member = saveMember("member@email.com");
      Member writer = saveMember("를riter@email.com");
      Post post = MockPost.create(writer);
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
      checkLikeCount(post.getId(), 0);
    }
  }

  @Nested
  @DisplayName("좋아요가 눌러져 있지 않을 때")
  class 좋아요가_눌러져있지_않을때 {

    @Test
    @DisplayName("취소 요청하면 예외 발생 & 좋아요 최종 개수 0개")
    public void 취소_요청() {
      Member member = saveMember("member@email.com");
      Member writer = saveMember("writer@email.com");
      Post post = MockPost.create(writer);

      assertThrows(NotFoundException.class, () -> {
        likeService.unlike(member.getId(), post.getId());
      });

      checkLikeCount(post.getId(), 0);
    }

    @Test
    @DisplayName("좋아요 요청하면 좋아요 최종 개수 1개")
    public void 한번요청() {
      Member member = saveMember("member@email.com");
      Member writer = saveMember("writer@email.com");
      Post post = MockPost.create(writer);

      likeService.like(member.getId(), post.getId());

      Optional<Like> like = likeRepository.findByMemberAndPost(member, post);
      assertTrue(like.isPresent());
      Like findLike = like.get();
      assertThat(findLike.getMember().getId(), is(member.getId()));
      assertThat(findLike.getPost().getId(), is(post.getId()));

      checkLikeCount(post.getId(), 1);

    }

    @Test
    @DisplayName("동시에 좋아요 여러번 요청하면 좋아요 최종 개수 1개")
    public void 동시에_여러개_요청() throws InterruptedException {
      // given
      Member member = saveMember("member@email.com");
      Member writer = saveMember("writer@email.com");
      Post post = MockPost.create(writer);

      // when
      ExecutorService executorService = Executors.newFixedThreadPool(30);
      int count = 30;
      CountDownLatch latch = new CountDownLatch(count);
      for (int i = 0; i < count; i++) {
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
      checkLikeCount(post.getId(), 1);
    }
  }

  @Test
  @DisplayName("여러명이 좋아요를 누를 수 있다.")
  void 여러명이_좋아요_누름(){
    Member writer = saveMember("writer@gmail.com");
    Post post = MockPost.create(writer);

    for(int i=0; i<4; i++){
      Member member = saveMember(i+"@gmail.com");
      likeService.like(member.getId(), post.getId());
    }

    checkLikeCount(post.getId(), 4);
  }

}









