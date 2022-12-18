package com.spring.boot.common.exception;

import com.spring.boot.common.MessageUtils;
import org.apache.commons.lang3.StringUtils;

public class NotFoundException extends ServiceRuntimeException{
    static final String MESSAGE_KEY = "error.notfound";
    static final String MESSAGE_DETAIL = "error.notfound.details";

    public NotFoundException(Class<?> cls, Object... values){
        this(cls.getSimpleName(), values);
    }

    public NotFoundException(String target, Object... values) {
        super(MESSAGE_KEY, MESSAGE_DETAIL, new String[]{target, (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
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
