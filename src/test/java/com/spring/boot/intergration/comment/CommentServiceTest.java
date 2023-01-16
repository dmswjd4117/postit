package com.spring.boot.intergration.comment;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.boot.comment.application.CommentService;
import com.spring.boot.comment.application.dto.CommentDto;
import com.spring.boot.comment.domain.CommentRepository;
import com.spring.boot.common.exception.AuthenticationFailException;
import com.spring.boot.common.exception.MemberNotFoundException;
import com.spring.boot.common.exception.PostAccessDeniedException;
import com.spring.boot.common.exception.PostNotFoundException;
import com.spring.boot.common.mock.MockPost;
import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CommentServiceTest extends IntegrationTest {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private CommentService commentService;
  @Autowired
  private ConnectionService connectionService;
  @Autowired
  private CommentRepository commentRepository;

  @Test
  @DisplayName("존재하지 않는 멤버아이디면 예외발생")
  void 댓글달기_실패() {
    Member postWriter = saveMember("writer@gmail.com");
    Post post = postRepository.save(MockPost.create(postWriter));
    assertThrows(MemberNotFoundException.class, () -> {
      commentService.createComment(-1L, "body", post.getId());
    });
  }

  @Test
  @DisplayName("존재하지 않는 포스트아이디면 예외발생")
  void 댓글달기_실패2() {
    Member commentWriter = saveMember("commentWriter@gmail.com");

    assertThrows(PostNotFoundException.class, () -> {
      commentService.createComment(commentWriter.getId(), "body", -1L);
    });
  }

  @Test
  @DisplayName("댓글 삭제할 수 있다")
  void 댓글_삭제() {
    // given
    Member postWriter = saveMember("writer@gmail.com");
    Post post = postRepository.save(MockPost.create(postWriter));
    Member commentWriter = saveMember("commentWriter@gmail.com");
    CommentDto comment = commentService.createComment(commentWriter.getId(), "body", post.getId());

    // when
    commentService.deleteComment(commentWriter.getId(), comment.getCommentId());

    // then
    assertTrue(commentRepository.findById(comment.getCommentId()).isEmpty());
  }

  @Test
  @DisplayName("댓글 작성자가 아니면 삭제할 수 없다")
  void 댓글_삭제_실패() {
    Member postWriter = saveMember("writer@gmail.com");
    Post post = postRepository.save(MockPost.create(postWriter));
    Member commentWriter = saveMember("commentWriter@gmail.com");
    CommentDto comment = commentService.createComment(commentWriter.getId(), "body", post.getId());

    assertThrows(AuthenticationFailException.class, () -> {
      commentService.deleteComment(100L, comment.getCommentId());
    });
  }


  @Nested
  @DisplayName("비공개 게시물일때")
  class 비공개_게시물 {

    Member postWriter;
    Post post;
    Member commentWriter;

    @BeforeEach
    void before() {
      postWriter = saveMember("writer@gmail.com");
      post = postRepository.save(
          new Post.Builder("title", "content", postWriter)
              .isPrivate(true)
              .build()
      );
      commentWriter = saveMember("comment@gmail.com");
    }

    @Test
    @DisplayName("팔로잉한 블로거의 글에 댓글을 달 수 있다.")
    void createCommentSuccess() {
      connectionService.follow(commentWriter.getId(), postWriter.getId());

      // when
      CommentDto commentDto = commentService.createComment(commentWriter.getId(), "content",
          post.getId());

      // then
      assertThat(commentDto.getBody(), is("content"));
    }

    @Test
    @DisplayName("팔로잉하지 않은 블로거의 글에는 댓글을 달 수 없다.")
    void createCommentFail_NotConnected() {
      assertThrows(PostAccessDeniedException.class, () -> {
        commentService.createComment(commentWriter.getId(), "content", post.getId());
      });
    }

  }

  @Nested
  @DisplayName("공개 게시물일때")
  class 공개_게시물 {

    Member postWriter;
    Post post;
    Member commentWriter;

    @BeforeEach
    void before() {
      postWriter = saveMember("writer@gmail.com");
      post = postRepository.save(MockPost.create(postWriter));
      commentWriter = saveMember("comment@gmail.com");
    }

    @Test
    @DisplayName("팔로잉한 블로거의 글에 댓글을 달 수 있다.")
    void createCommentSuccess() {
      // given
      connectionService.follow(commentWriter.getId(), postWriter.getId());

      // when
      CommentDto comment = commentService.createComment(commentWriter.getId(), "content",
          post.getId());

      // then
      assertThat(comment.getBody(), is("content"));
    }

    @Test
    @DisplayName("팔로잉하지 않은 블로거의 글에도 댓글 달 수 있다.")
    void createCommentFail_NotConnected() {
      // when
      CommentDto comment = commentService.createComment(commentWriter.getId(), "content",
          post.getId());

      // then
      assertThat(comment.getBody(), is("content"));
    }
  }

}





