package com.spring.boot.like.presentation.dto;

import com.spring.boot.like.application.dto.LikeMemberResponseDto;
import com.spring.boot.like.application.dto.LikeMemberResponseDto.MemberDto;
import lombok.Getter;

@Getter
public class LikeMemberResponse {

  private Long id;
  private MemberInfo member;

  private LikeMemberResponse(Long id, MemberInfo member) {
    this.id = id;
    this.member = member;
  }

  public static LikeMemberResponse from(LikeMemberResponseDto likeMemberDto) {
    MemberDto memberInfo = likeMemberDto.getMemberDto();
    return new LikeMemberResponse(likeMemberDto.getLikeId(),
        new MemberInfo(memberInfo.getMemberId(), memberInfo.getMemberName()));
  }

  @Getter
  public static class MemberInfo {

    private Long memberId;
    private String name;

    protected MemberInfo(Long memberId, String name) {
      this.memberId = memberId;
      this.name = name;
    }
  }
}
