package com.spring.boot.error;

import com.spring.boot.util.MessageUtils;

public class DuplicatedEmailException extends  ServiceRuntimeException{

    public static final String MESSAGE_KEY = "error.duplicated";

    public static final String MESSAGE_DETAIL = "error.duplicated.details";

    public DuplicatedEmailException(String message) {
        super(MESSAGE_KEY, MESSAGE_DETAIL, new Object[]{message});
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
