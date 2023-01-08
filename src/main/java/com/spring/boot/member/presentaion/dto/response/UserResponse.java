package com.spring.boot.member.presentaion.dto.response;

import com.spring.boot.member.application.dto.UserInfoDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserResponse {

  private Long id;
  private String email;
  private String profileImagePath;

  public static UserResponse from(UserInfoDto user) {
    return UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .profileImagePath(user.getProfileImagePath())
        .build();
  }
}
