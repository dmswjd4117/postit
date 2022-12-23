package com.spring.boot.member.application.dto;

import com.spring.boot.member.domain.Member;

public class MemberDtoMapper {

  public static MemberInfoDto memberInfoDto(Member member){
    return MemberInfoDto.builder()
        .id(member.getId())
        .email(member.getEmail())
        .name(member.getName())
        .profileImagePath(member.getProfileImagePath())
        .grantedAuthorities(member.getGrantedAuthorities())
        .build();
  }

}
