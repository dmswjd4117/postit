package com.spring.boot.comment.application;

import com.spring.boot.comment.application.dto.CommentResponseDto;
import com.spring.boot.comment.domain.Comment;
import com.spring.boot.comment.domain.CommentRepository;
import com.spring.boot.exception.AuthenticationFailException;
import com.spring.boot.exception.MemberNotFoundException;
import com.spring.boot.exception.PostAccessDeniedException;
import com.spring.boot.exception.PostNotFoundException;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.MemberRepository;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final MemberRepository memberRepository;
  private final PostQueryService postQueryService;

  public CommentService(CommentRepository commentRepository, PostRepository postRepository,
      MemberRepository memberRepository,
      PostQueryService postQueryService) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.memberRepository = memberRepository;
    this.postQueryService = postQueryService;
  }

  @Transactional
  public CommentResponseDto createComment(Long writerId, String body, Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException(postId));
    Member writer = memberRepository.findById(writerId)
        .orElseThrow(()->new MemberNotFoundException(writerId));

    if (!postQueryService.checkMemberAccessAuth(postId, writerId)) {
      throw new PostAccessDeniedException();
    }

    Comment comment = commentRepository.save(new Comment(writer, post, body));
    return CommentResponseDto.from(comment);
  }

  @Transactional
  public Long deleteComment(Long memberId, Long commentId) {
    Comment comment = commentRepository.findByIdAndWriterId(commentId, memberId)
        .orElseThrow(() -> new AuthenticationFailException("존재하지 않는 댓글입니다"));
    commentRepository.delete(comment);
    return commentId;
  }

}
