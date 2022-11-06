package com.spring.boot.comment.application;

import com.spring.boot.comment.domain.Comment;
import com.spring.boot.common.error.NotConnectedException;
import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.member.Member;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.application.dto.PostRequest;
import com.spring.boot.post.domain.Post;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
class CommentServiceTest {

  private final Member POST_WRITER = new Member("email@gmail.com", "password", "post");
  private final Member COMMENT_WRITER = new Member("email2@gmail.com", "password", "comment");
  private final Post POST = new Post("title", "post-body", POST_WRITER);

  @Autowired
  private MemberService memberService;
  @Autowired
  private PostService postService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private ConnectionService connectionService;

  private final String COMMENT_BODY = "wow...good!";;

  Member getPostWriter(){
    return memberService.register(POST_WRITER);
  }

  Member getCommentWriter(){
    return memberService.register(COMMENT_WRITER);
  }

  Post getPost(Long writerId){
    PostRequest postRequest = new PostRequest(
        POST.getTitle(), POST.getBody(), emptyList()
    );
    return postService.createPost(writerId, postRequest, emptyList());
  }

  @Test
  @DisplayName("팔로잉한 블로거의 글에 댓글을 달 수 있다.")
  void createCommentSuccess(){
    // given
    Member postWriter = getPostWriter();
    Member commentWriter = getCommentWriter();
    Post post = getPost(postWriter.getId());
    connectionService.follow(commentWriter.getId(), postWriter.getId());

    // when
    Comment comment = commentService.createComment(commentWriter.getId(), COMMENT_BODY, post.getId());

    // then
    assertAll(
        ()->{
          assertEquals(comment.getBody(), COMMENT_BODY);
          assertEquals(comment.getWriter().getId(), commentWriter.getId());
          assertEquals(comment.getPost().getId(), post.getId());
        }
    );
  }

  @Test
  @DisplayName("팔로잉하지 않은 블로거의 글에는 댓글을 달 수 없다..")
  void createCommentFail_NotConnected(){
    // given
    Member postWriter = getPostWriter();
    Member commentWriter = getCommentWriter();
    Post post = getPost(postWriter.getId());

    assertThrows(NotConnectedException.class, ()->{
      commentService.createComment(commentWriter.getId(), COMMENT_BODY, post.getId());
    });
  }

}





