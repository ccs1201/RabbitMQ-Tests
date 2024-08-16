package com.ccs.rabbitmqtests;

import org.springframework.boot.SpringApplication;

public class TestRabbitMqTestsApplication {

    public static void main(String[] args) {
        SpringApplication.from(RabbitMqTestsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
