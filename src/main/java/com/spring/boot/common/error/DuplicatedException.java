package com.spring.boot.common.error;

import com.spring.boot.common.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;

public class DuplicatedException extends  ServiceRuntimeException{

    public static final String MESSAGE_KEY = "error.duplicated";

    public static final String MESSAGE_DETAIL = "error.duplicated.details";

    public DuplicatedException(String message) {
        super(MESSAGE_KEY, MESSAGE_DETAIL, new Object[]{message});
    }
    public DuplicatedException(Class<?> cls, Object... values){
        this(cls.getSimpleName(), values);
    }

    public DuplicatedException(String target, Object... values) {
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
