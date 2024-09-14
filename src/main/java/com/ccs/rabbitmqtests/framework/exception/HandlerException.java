package com.ccs.rabbitmqtests.framework.exception;

public class HandlerException extends RuntimeException {

    public HandlerException(Throwable e) {
        super(e);
    }

    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(String message, ClassCastException e) {
        super(message, e);
    }
}
