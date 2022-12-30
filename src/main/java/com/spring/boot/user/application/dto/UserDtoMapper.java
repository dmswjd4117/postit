package com.spring.boot.user.application.dto;

import com.spring.boot.user.domain.User;

public class UserDtoMapper {

  public static UserInfoDto memberInfoDto(
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
