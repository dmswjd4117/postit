package com.spring.boot.comment.application;

import com.spring.boot.comment.domain.Comment;
import com.spring.boot.comment.domain.CommentRepository;
import com.spring.boot.common.exception.NotConnectedException;
import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.user.application.UserService;
import com.spring.boot.user.domain.Member;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final ConnectionService connectionService;
  private final UserService userService;

  public CommentService(CommentRepository commentRepository, PostRepository postRepository,
      ConnectionService connectionService, UserService userService) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.connectionService = connectionService;
    this.userService = userService;
  }

  @Transactional
  public Comment createComment(Long writerId, String body, Long postId) {
    Member commentWriter = userService.findById(writerId);

    return postRepository.findById(postId)
        .map(findPost -> {
          Member postWriter = findPost.getWriter();
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
