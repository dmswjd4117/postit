package com.spring.boot.member.application.dto;

import com.spring.boot.member.domain.Member;

public class MemberDtoMapper {

  public static com.spring.boot.member.application.dto.MemberInfoDto memberInfoDto(
      Member member){
    return com.spring.boot.member.application.dto.MemberInfoDto.builder()
        .id(member.getId())
        .email(member.getEmail())
        .name(member.getName())
        .profileImagePath(member.getProfileImagePath())
        .grantedAuthority(member.getGrantedAuthority())
        .build();
  }

}
