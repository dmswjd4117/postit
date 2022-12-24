package com.spring.boot.member.presentaion.dto.request;

import com.spring.boot.member.application.dto.MemberInfoDto;
import com.spring.boot.member.presentaion.dto.response.MemberResponse;

public class MemberMapper {

  public static MemberResponse memberResponse(MemberInfoDto member) {
    return MemberResponse.builder()
        .id(member.getId())
        .email(member.getEmail())
        .profileImagePath(member.getProfileImagePath())
        .build();
  }

}
