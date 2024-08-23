package com.ccs.rabbitmqtests.framework;

public interface Handler<T> {

    void handle(T input);
}
