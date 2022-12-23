package com.spring.boot.intergration.comment;

import com.spring.boot.comment.application.CommentService;
import com.spring.boot.comment.domain.Comment;
import com.spring.boot.common.exception.NotConnectedException;
import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.member.application.dto.MemberInfoDto;
import com.spring.boot.member.domain.role.RoleName;
import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.presentaion.dto.request.PostCreateRequest;
import com.spring.boot.post.domain.Post;
import com.spring.boot.common.DatabaseCleanUp;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
class CommentServiceTest {

  private final Member POST_WRITER = new Member("email@gmail.com", "password", "post");
  private final Member COMMENT_WRITER = new Member("email2@gmail.com", "password", "comment");
  private final Post POST = new Post("title", "post-content", POST_WRITER);
  private final String COMMENT_BODY = "wow...good!";;

  @Autowired
  DatabaseCleanUp databaseCleanUp;
  @Autowired
  private MemberService memberService;
  @Autowired
  private PostService postService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private ConnectionService connectionService;
  @AfterEach
  void cleanUp(){
    databaseCleanUp.clear();
  }

  MemberInfoDto savePostWriter(){
    return memberService.register(POST_WRITER, RoleName.MEMBER);
  }

  MemberInfoDto saveCommentWriter(){
    return memberService.register(COMMENT_WRITER, RoleName.MEMBER);
  }

  Post savePost(Long writerId){
    PostCreateRequest postCreateRequest = new PostCreateRequest(
        POST.getTitle(), POST.getContent(), emptyList()
    );
    return postService.createPost(writerId, postCreateRequest, emptyList());
  }

  @Test
  @DisplayName("팔로잉한 블로거의 글에 댓글을 달 수 있다.")
  void createCommentSuccess(){
    // given
    MemberInfoDto postWriter = savePostWriter();
    MemberInfoDto commentWriter = saveCommentWriter();
    Post post = savePost(postWriter.getId());
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
  @DisplayName("팔로잉하지 않은 블로거의 글에는 댓글을 달 수 없다.")
  void createCommentFail_NotConnected(){
    // given
    MemberInfoDto postWriter = savePostWriter();
    MemberInfoDto commentWriter = saveCommentWriter();
    Post post = savePost(postWriter.getId());

    assertThrows(NotConnectedException.class, ()->{
      commentService.createComment(commentWriter.getId(), COMMENT_BODY, post.getId());
    });
  }

  @Test
  @DisplayName("존재하지 않는 멤버아이디일경우 예외발생")
  void 댓글달기_실패(){
    MemberInfoDto postWriter = savePostWriter();
    Post post = savePost(postWriter.getId());
    assertThrows(NotFoundException.class, ()->{
      commentService.createComment(-1L, "body", post.getId());
    });
  }

  @Test
  @DisplayName("존재하지 않는 포스트 아이디 예외발생")
  void 댓글달기_실패2(){
    MemberInfoDto commentWriter = saveCommentWriter();
    assertThrows(NotFoundException.class, ()->{
      commentService.createComment(commentWriter.getId(), "body", -1L);
    });
  }

}





