package com.spring.boot.exception;

public abstract class ServiceRuntimeException extends RuntimeException{

    private final String messageKey;
    private final String detailKey;
    private final Object[] params;
    protected ServiceRuntimeException(String messageKey, String detailKey, Object[] params) {
        this.messageKey = messageKey;
        this.detailKey = detailKey;
        this.params = params;
    }
    public String getMessageKey() {
        return messageKey;
    }
    public String getDetailKey() {
        return detailKey;
    }
    public Object[] getParams() {
        return params;
    }
}
