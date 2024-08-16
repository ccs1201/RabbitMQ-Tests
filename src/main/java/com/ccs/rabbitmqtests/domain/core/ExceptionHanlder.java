package com.ccs.rabbitmqtests.domain.core;


import com.ccs.rabbitmqtests.domain.core.exceptions.AppRuntimeException;
import com.ccs.rabbitmqtests.domain.core.model.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHanlder {

    @ExceptionHandler(AppRuntimeException.class)
    public ExceptionResponse handleCajuException(AppRuntimeException ex) {
        log.error("Erro ao processar transação", ex);
        return new ExceptionResponse("07");
    }

    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleException(Exception ex) {
        log.error("Erro ao processar transação", ex);
        return new ExceptionResponse("07");
    }
}
