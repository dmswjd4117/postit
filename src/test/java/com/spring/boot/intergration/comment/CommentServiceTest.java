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
//    UserInfoDto postWriter = saveMember();
//    UserInfoDto commentWriter = saveMember();
//    PostCreateRequest postCreateRequest = new PostCreateRequest(TITLE, CONTENT, TAG_NAMES);
//    PostResponseDto postInfoDto = savePost(postWriter.getId(), postCreateRequest, IMAGES);
//    connectionService.follow(commentWriter.getId(), postWriter.getId());
//
//    // when
//    Comment comment = commentService.createComment(commentWriter.getId(), COMMENT_CONTENT,
//        postInfoDto.getId());
//
//    // then
//    assertAll(
//        () -> {
//          assertEquals(comment.getBody(), COMMENT_CONTENT);
//          assertEquals(comment.getWriter().getId(), commentWriter.getId());
//          assertEquals(comment.getPost().getId(), postInfoDto.getId());
//        }
//    );
//  }
//
//  @Test
//  @DisplayName("팔로잉하지 않은 블로거의 글에는 댓글을 달 수 없다.")
//  void createCommentFail_NotConnected() {
//    // given
//    UserInfoDto postWriter = saveMember();
//    UserInfoDto commentWriter = saveMember();
//    PostCreateRequest postCreateRequest = new PostCreateRequest(TITLE, CONTENT, TAG_NAMES);
//    PostResponseDto postInfoDto = savePost(postWriter.getId(), postCreateRequest, IMAGES);
//
//    assertThrows(NotConnectedException.class, () -> {
//      commentService.createComment(commentWriter.getId(), COMMENT_CONTENT, postInfoDto.getId());
//    });
//  }
//
//  @Test
//  @DisplayName("존재하지 않는 멤버아이디일경우 예외발생")
//  void 댓글달기_실패() {
//    UserInfoDto postWriter = saveMember();
//    PostCreateRequest postCreateRequest = new PostCreateRequest(TITLE, CONTENT, TAG_NAMES);
//    PostResponseDto postInfoDto = savePost(postWriter.getId(), postCreateRequest, IMAGES);
//    assertThrows(NotFoundException.class, () -> {
//      commentService.createComment(-1L, "body", postInfoDto.getId());
//    });
//  }
//
//  @Test
//  @DisplayName("존재하지 않는 포스트 아이디 예외발생")
//  void 댓글달기_실패2() {
//    UserInfoDto commentWriter = saveMember();
//    assertThrows(NotFoundException.class, () -> {
//      commentService.createComment(commentWriter.getId(), "body", -1L);
//    });
//  }

}





