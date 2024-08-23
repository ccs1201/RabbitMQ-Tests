package com.ccs.rabbitmqtests.api.v1.handlers.annotations;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface HandlerImpl {
    @NotNull
    Class<?> value();
}
