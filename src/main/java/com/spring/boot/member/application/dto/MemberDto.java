package com.spring.boot.member.application.dto;

import com.spring.boot.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Builder
@Getter
public class MemberDto {
  private Long id;
  private String name;
  private String email;
  private String profileImagePath;
  private GrantedAuthority grantedAuthority;

  public static MemberDto from(
      Member member){
    return MemberDto.builder()
        .id(member.getId())
        .email(member.getEmail())
        .name(member.getName())
        .profileImagePath(member.getProfileImagePath())
        .grantedAuthority(member.getGrantedAuthority())
        .build();
  }
}
