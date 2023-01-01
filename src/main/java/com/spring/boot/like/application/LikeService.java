package com.spring.boot.like.application;

import com.spring.boot.like.domain.Like;
import com.spring.boot.like.domain.LikeRepository;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.domain.Post;
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
  public void like(Long memberId, Long postId) {
    Member member = memberService.findByMemberId(memberId);
    Post post = postService.findByPostId(postId);
    Like like = new Like(member, post);
    likeRepository.findByMemberAndPost(member, post)
        .ifPresentOrElse(
            likeRepository::delete,
            () -> likeRepository.save(like)
        );
  }


}
