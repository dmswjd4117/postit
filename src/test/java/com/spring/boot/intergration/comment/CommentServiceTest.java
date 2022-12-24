package com.spring.boot.intergration.comment;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.spring.boot.comment.domain.Comment;
import com.spring.boot.common.exception.NotConnectedException;
import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.member.application.dto.MemberInfoDto;
import com.spring.boot.post.application.dto.PostInfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class CommentServiceTest extends IntegrationTest {

  private String COMMENT_CONTENT = "content";

  @Test
  @DisplayName("팔로잉한 블로거의 글에 댓글을 달 수 있다.")
  void createCommentSuccess() {
    // given
    MemberInfoDto postWriter = saveMember();
    MemberInfoDto commentWriter = saveMember();
    PostInfoDto postInfoDto = savePost(postWriter.getId());
    connectionService.follow(commentWriter.getId(), postWriter.getId());

    // when
    Comment comment = commentService.createComment(commentWriter.getId(), COMMENT_CONTENT,
        postInfoDto.getId());

    // then
    assertAll(
        () -> {
          assertEquals(comment.getBody(), COMMENT_CONTENT);
          assertEquals(comment.getWriter().getId(), commentWriter.getId());
          assertEquals(comment.getPost().getId(), postInfoDto.getId());
        }
    );
  }

  @Test
  @DisplayName("팔로잉하지 않은 블로거의 글에는 댓글을 달 수 없다.")
  void createCommentFail_NotConnected() {
    // given
    MemberInfoDto postWriter = saveMember();
    MemberInfoDto commentWriter = saveMember();
    PostInfoDto postInfoDto = savePost(postWriter.getId());

    assertThrows(NotConnectedException.class, () -> {
      commentService.createComment(commentWriter.getId(), COMMENT_CONTENT, postInfoDto.getId());
    });
  }

  @Test
  @DisplayName("존재하지 않는 멤버아이디일경우 예외발생")
  void 댓글달기_실패() {
    MemberInfoDto postWriter = saveMember();
    PostInfoDto postInfoDto = savePost(postWriter.getId());
    assertThrows(NotFoundException.class, () -> {
      commentService.createComment(-1L, "body", postInfoDto.getId());
    });
  }

  @Test
  @DisplayName("존재하지 않는 포스트 아이디 예외발생")
  void 댓글달기_실패2() {
    MemberInfoDto commentWriter = saveMember();
    assertThrows(NotFoundException.class, () -> {
      commentService.createComment(commentWriter.getId(), "body", -1L);
    });
  }

}





