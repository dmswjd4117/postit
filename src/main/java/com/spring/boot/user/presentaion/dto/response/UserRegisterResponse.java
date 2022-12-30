package com.spring.boot.user.presentaion.dto.response;

public class UserRegisterResponse {

  private UserResponse member;

  public UserRegisterResponse(UserResponse member) {
    this.member = member;
  }

  public UserResponse getMember() {
    return member;
  }
}
