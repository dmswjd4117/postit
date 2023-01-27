package com.spring.boot.exception;

public class MemberNotFoundException extends RuntimeException {

  public MemberNotFoundException(Long memberId) {
    super("존재하지 않는 유저입니다 멤버아이디: " + memberId);
  }
}
