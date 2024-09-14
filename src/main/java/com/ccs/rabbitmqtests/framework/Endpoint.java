package com.ccs.rabbitmqtests.framework;

public interface Endpoint<I, R> {

    R handle(I input);
}
