package com.spring.boot.exception;

import org.apache.commons.lang3.StringUtils;

public class NotConnectedException extends ServiceRuntimeException {

  static final String MESSAGE_KEY = "error.notconnected";
  static final String MESSAGE_DETAIL = "error.notconnected.details";

  public NotConnectedException(Class<?> cls, Object... values) {
    this(cls.getSimpleName(), values);
  }

  public NotConnectedException(String target, Object... values) {
    super(MESSAGE_KEY, MESSAGE_DETAIL, new String[]{target,
        (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
  }
  @Override
  public String getMessage() {
    return MessageUtils.getMessage(getDetailKey(), getParams());
  }

  @Override
  public String toString() {
    return MessageUtils.getMessage(getMessageKey());
  }
}
