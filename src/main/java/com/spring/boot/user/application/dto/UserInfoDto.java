package com.spring.boot.user.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Builder
@Getter
public class UserInfoDto {
  private Long id;
  private String name;
  private String email;
  private String profileImagePath;
  private GrantedAuthority grantedAuthority;
}
