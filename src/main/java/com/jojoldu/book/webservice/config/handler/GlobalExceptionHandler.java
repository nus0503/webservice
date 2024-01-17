package com.jojoldu.book.webservice.config.handler;

import com.jojoldu.book.webservice.exception.dto.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus
    @ExceptionHandler(Exception.class)
    public ErrorResult exceptionHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExceptionHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }
}
