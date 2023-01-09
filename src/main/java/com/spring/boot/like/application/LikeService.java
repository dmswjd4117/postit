package com.spring.boot.like.application;

import com.spring.boot.common.exception.DuplicatedException;
import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.like.application.dto.LikeDto;
import com.spring.boot.like.application.dto.LikeMemberDto;
import com.spring.boot.like.domain.Like;
import com.spring.boot.like.domain.LikeRepository;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.domain.Post;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

  private final LikeRepository likeRepository;
  private final MemberService memberService;
  private final PostService postService;

  public LikeService(LikeRepository likeRepository, MemberService memberService,
      PostService postService) {
    this.likeRepository = likeRepository;
    this.memberService = memberService;
    this.postService = postService;
  }

  @Transactional
  public LikeDto like(Long memberId, Long postId) {
    Member member = memberService.findByMemberId(memberId);
    Post post = postService.findByPostId(postId);

    try {
      likeRepository.save(new Like(member, post));
      post.like();
    } catch (DataIntegrityViolationException exception) {
      throw new DuplicatedException(Like.class, "좋아요가 이미 존재합니다");
    }

    return new LikeDto(post.getLikeTotalCount(), true);
  }

  @Transactional
  public LikeDto unlike(Long memberId, Long postId) {
    Member member = memberService.findByMemberId(memberId);
    Post post = postService.findByPostId(postId);

    Like findLike = likeRepository.findByMemberAndPost(member, post)
        .orElseThrow(() -> new NotFoundException(Like.class, "좋아요가 존재하지 않습니다"));

    likeRepository.delete(findLike);
    post.unlike();

    return new LikeDto(post.getLikeTotalCount(), false);
  }

  @Transactional(readOnly = true)
  public List<LikeMemberDto> getLikeMembers(Long postId) {
    Post post = postService.findByPostId(postId);
    return likeRepository.findByPost(post).stream()
        .map(like -> LikeMemberDto.from(like, like.getMember()))
        .collect(Collectors.toList());
  }


}
