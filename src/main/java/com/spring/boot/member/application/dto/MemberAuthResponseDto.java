package com.spring.boot.member.application.dto;

import com.spring.boot.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Builder
public class MemberAuthResponseDto {
  private Long id;
  private String name;
  private String email;
  private GrantedAuthority grantedAuthority;

  public static MemberAuthResponseDto from(
      Member member) {
    return MemberAuthResponseDto.builder()
        .id(member.getId())
        .email(member.getEmail())
        .name(member.getName())
        .grantedAuthority(member.getGrantedAuthority())
        .build();
  }
}
