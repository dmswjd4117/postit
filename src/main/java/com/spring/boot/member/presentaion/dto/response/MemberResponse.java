package com.spring.boot.member.presentaion.dto.response;

import com.spring.boot.member.application.dto.MemberResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class MemberResponse {

  private Long id;
  private String email;
  private String profileImagePath;

  public static MemberResponse from(MemberResponseDto user) {
    return MemberResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .profileImagePath(user.getProfileImagePath())
        .build();
  }
}
