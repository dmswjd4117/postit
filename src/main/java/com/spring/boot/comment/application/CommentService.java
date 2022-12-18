package com.spring.boot.comment.application;

import com.spring.boot.comment.domain.Comment;
import com.spring.boot.comment.domain.CommentRepository;
import com.spring.boot.common.error.NotConnectedException;
import com.spring.boot.common.error.NotFoundException;
import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.member.Member;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final ConnectionService connectionService;
  private final MemberService memberService;

  public CommentService(CommentRepository commentRepository, PostRepository postRepository,
      ConnectionService connectionService, MemberService memberService) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.connectionService = connectionService;
    this.memberService = memberService;
  }

  @Transactional
  public Comment createComment(Long writerId, String body, Long postId) {
    Member commentWriter = memberService.findById(writerId);

    return postRepository.findById(postId)
        .map(findPost -> {
          Member postWriter = findPost.getMember();
          if (!postWriter.getId().equals(writerId) &&
              !connectionService.checkMemberFollowsTargetMember(writerId, postWriter.getId())) {
            throw new NotConnectedException(Member.class, writerId, "doesn't follow",
                postWriter.getId());
          }
          return commentRepository.save(new Comment(commentWriter, findPost, body));
        })
        .orElseThrow(() -> new NotFoundException(Post.class, "id", postId));
  }
}
