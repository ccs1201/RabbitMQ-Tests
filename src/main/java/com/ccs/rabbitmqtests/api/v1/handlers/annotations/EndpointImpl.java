package com.ccs.rabbitmqtests.api.v1.handlers.annotations;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface EndpointImpl {
    Class<?> forClass();
}
