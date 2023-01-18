package com.spring.boot.like.application.dto;

import com.spring.boot.like.domain.Like;
import com.spring.boot.member.domain.Member;
import lombok.Getter;

@Getter
public class LikeMemberResponseDto {

  private Long likeId;
  private MemberDto memberDto;

  private LikeMemberResponseDto(Long likeId, MemberDto memberDto) {
    this.likeId = likeId;
    this.memberDto = memberDto;
  }

  public static LikeMemberResponseDto from(Like like, Member member) {
    return new LikeMemberResponseDto(like.getId(), MemberDto.from(member));
  }

  @Getter
  public static class MemberDto {

    private Long memberId;
    private String memberName;

    private MemberDto(Long memberId, String memberName) {
      this.memberId = memberId;
      this.memberName = memberName;
    }

    public static MemberDto from(Member member) {
      return new MemberDto(member.getId(), member.getName());
    }
  }
}
