package com.spring.boot.exception;


import org.springframework.security.access.AccessDeniedException;

public class PostAccessDeniedException extends AccessDeniedException {

  public PostAccessDeniedException() {
    super("접근 권한이 없습니다.");
  }
}
