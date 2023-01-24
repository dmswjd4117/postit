package com.spring.boot.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationFailException  extends AuthenticationException {
  public AuthenticationFailException(String msg) {
    super(msg);
  }

  public AuthenticationFailException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
