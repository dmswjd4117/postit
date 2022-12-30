package com.spring.boot.user.application.dto;

import com.spring.boot.user.domain.User;
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

  public static UserInfoDto from(
      User user){
    return UserInfoDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .profileImagePath(user.getProfileImagePath())
        .grantedAuthority(user.getGrantedAuthority())
        .build();
  }
}
