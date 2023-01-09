package com.spring.boot.like.presentation.dto;

import com.spring.boot.like.application.dto.LikeMemberDto;
import com.spring.boot.like.application.dto.LikeMemberDto.MemberDto;
import lombok.Getter;

@Getter
public class LikeMember {

  private Long likeId;
  private MemberInfo memberDto;

  private LikeMember(Long likeId, MemberInfo memberDto) {
    this.likeId = likeId;
    this.memberDto = memberDto;
  }

  public static LikeMember from(LikeMemberDto likeMemberDto) {
    MemberDto memberInfo = likeMemberDto.getMemberDto();
    return new LikeMember(likeMemberDto.getLikeId(),
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
