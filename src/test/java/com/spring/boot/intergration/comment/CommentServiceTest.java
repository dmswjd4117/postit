package com.spring.boot.intergration.comment;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.spring.boot.intergration.IntegrationTest;


class CommentServiceTest extends IntegrationTest {


//
//  @Test
//  @DisplayName("팔로잉한 블로거의 글에 댓글을 달 수 있다.")
//  void createCommentSuccess() {
//    // given
//    MemberDto postWriter = saveMember();
//    MemberDto commentWriter = saveMember();
//    PostCreateRequest postCreateRequest = new PostCreateRequest(TITLE, CONTENT, TAG_NAMES);
//    PostResponse postInfoDto = savePost(postWriter.getMemberId(), postCreateRequest, IMAGES);
//    connectionService.follow(commentWriter.getMemberId(), postWriter.getMemberId());
//
//    // when
//    Comment comment = commentService.createComment(commentWriter.getMemberId(), COMMENT_CONTENT,
//        postInfoDto.getMemberId());
//
//    // then
//    assertAll(
//        () -> {
//          assertEquals(comment.getBody(), COMMENT_CONTENT);
//          assertEquals(comment.getWriter().getMemberId(), commentWriter.getMemberId());
//          assertEquals(comment.getPost().getMemberId(), postInfoDto.getMemberId());
//        }
//    );
//  }
//
//  @Test
//  @DisplayName("팔로잉하지 않은 블로거의 글에는 댓글을 달 수 없다.")
//  void createCommentFail_NotConnected() {
//    // given
//    MemberDto postWriter = saveMember();
//    MemberDto commentWriter = saveMember();
//    PostCreateRequest postCreateRequest = new PostCreateRequest(TITLE, CONTENT, TAG_NAMES);
//    PostResponse postInfoDto = savePost(postWriter.getMemberId(), postCreateRequest, IMAGES);
//
//    assertThrows(NotConnectedException.class, () -> {
//      commentService.createComment(commentWriter.getMemberId(), COMMENT_CONTENT, postInfoDto.getMemberId());
//    });
//  }
//
//  @Test
//  @DisplayName("존재하지 않는 멤버아이디일경우 예외발생")
//  void 댓글달기_실패() {
//    MemberDto postWriter = saveMember();
//    PostCreateRequest postCreateRequest = new PostCreateRequest(TITLE, CONTENT, TAG_NAMES);
//    PostResponse postInfoDto = savePost(postWriter.getMemberId(), postCreateRequest, IMAGES);
//    assertThrows(NotFoundException.class, () -> {
//      commentService.createComment(-1L, "body", postInfoDto.getMemberId());
//    });
//  }
//
//  @Test
//  @DisplayName("존재하지 않는 포스트 아이디 예외발생")
//  void 댓글달기_실패2() {
//    MemberDto commentWriter = saveMember();
//    assertThrows(NotFoundException.class, () -> {
//      commentService.createComment(commentWriter.getMemberId(), "body", -1L);
//    });
//  }

}





