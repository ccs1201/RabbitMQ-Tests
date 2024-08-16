package com.ccs.rabbitmqtests.domain.core.exceptions;

public class AppRuntimeException extends RuntimeException {
    public AppRuntimeException(String msg) {
        super(msg);
    }
}
