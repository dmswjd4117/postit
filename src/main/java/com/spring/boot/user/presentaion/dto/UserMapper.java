package com.spring.boot.user.presentaion.dto;

import com.spring.boot.user.application.dto.UserInfoDto;
import com.spring.boot.user.presentaion.dto.response.UserResponse;

public class UserMapper {

  public static UserResponse memberResponse(UserInfoDto member) {
    return UserResponse.builder()
        .id(member.getId())
        .email(member.getEmail())
        .profileImagePath(member.getProfileImagePath())
        .build();
  }

}
