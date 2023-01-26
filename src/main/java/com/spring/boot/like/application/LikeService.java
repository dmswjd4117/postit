package com.spring.boot.like.application;

import com.spring.boot.exception.DuplicatedException;
import com.spring.boot.exception.LikeNotFoundException;
import com.spring.boot.exception.PostNotFoundException;
import com.spring.boot.like.application.dto.LikeResponseDto;
import com.spring.boot.like.application.dto.LikeMemberResponseDto;
import com.spring.boot.like.domain.Like;
import com.spring.boot.like.domain.LikeRepository;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

  private final LikeRepository likeRepository;
  private final MemberService memberService;
  private final PostRepository postRepository;

  public LikeService(LikeRepository likeRepository, MemberService memberService,
      PostRepository postRepository) {
    this.likeRepository = likeRepository;
    this.memberService = memberService;
    this.postRepository = postRepository;
  }

  @Transactional
  public LikeResponseDto like(Long memberId, Long postId) {
    Member member = memberService.findByMemberId(memberId);
    Post post = postRepository.findByPostId(postId)
        .orElseThrow(() -> new PostNotFoundException(postId));

    try {
      likeRepository.save(new Like(member, post));
      post.like();
    } catch (DataIntegrityViolationException exception) {
      throw new DuplicatedException(Like.class, "좋아요가 이미 존재합니다");
    }

    return new LikeResponseDto(post.getLikeTotalCount(), true);
  }

  @Transactional
  public LikeResponseDto unlike(Long memberId, Long postId) {
    Member member = memberService.findByMemberId(memberId);
    Post post = postRepository.findByPostId(postId)
        .orElseThrow(() -> new PostNotFoundException(postId));

    Like findLike = likeRepository.findByMemberAndPost(member, post)
        .orElseThrow(LikeNotFoundException::new);

    likeRepository.delete(findLike);
    post.unlike();

    return new LikeResponseDto(post.getLikeTotalCount(), false);
  }

  @Transactional(readOnly = true)
  public List<LikeMemberResponseDto> getLikeMembers(Long postId) {
    Post post = postRepository.findByPostId(postId)
        .orElseThrow(() -> new PostNotFoundException(postId));
    return likeRepository.findByPost(post).stream()
        .map(like -> LikeMemberResponseDto.from(like, like.getMember()))
        .collect(Collectors.toList());
  }


}
