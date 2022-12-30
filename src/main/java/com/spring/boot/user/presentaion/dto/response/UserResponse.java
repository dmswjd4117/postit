package com.spring.boot.user.presentaion.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
  private Long id;
  private String email;
  private String profileImagePath;
}
