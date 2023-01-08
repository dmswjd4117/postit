package com.spring.boot.like.application.dto;

import com.spring.boot.like.domain.Like;
import com.spring.boot.member.domain.Member;

public class LikeDto {

  private Long likeId;
  private MemberDto memberDto;

  public LikeDto(Like like) {
    this.likeId = like.getId();
    this.memberDto = new MemberDto(like.getMember());
  }

  public static class MemberDto {

    private Long memberId;
    private String memberName;

    public MemberDto(Member member) {
      this.memberId = member.getId();
      this.memberName = member.getName();
    }
  }
}
