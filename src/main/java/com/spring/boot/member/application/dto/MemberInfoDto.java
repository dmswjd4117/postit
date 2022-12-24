package com.spring.boot.member.application.dto;

import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Builder
@Getter
public class MemberInfoDto {
  private Long id;
  private String name;
  private String email;
  private String profileImagePath;
  private GrantedAuthority grantedAuthority;
}
