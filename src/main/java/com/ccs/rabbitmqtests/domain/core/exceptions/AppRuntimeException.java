package com.ccs.rabbitmqtests.domain.core.exceptions;

public class AppRuntimeException extends RuntimeException {
    public AppRuntimeException(String msg) {
        super(msg);
    }

    public AppRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AppRuntimeException(Throwable cause) {
        super(cause);
    }
}
