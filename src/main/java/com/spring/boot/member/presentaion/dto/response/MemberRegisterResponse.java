package com.spring.boot.member.presentaion.dto.response;

public class MemberRegisterResponse {

  private MemberResponse member;

  public MemberRegisterResponse(MemberResponse member) {
    this.member = member;
  }

  public MemberResponse getMember() {
    return member;
  }
}
