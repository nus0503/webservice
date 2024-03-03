package com.jojoldu.book.webservice.config.handler;

import com.jojoldu.book.webservice.config.exception.BusinessExceptionHandler;
import com.jojoldu.book.webservice.config.exception.dto.ErrorResult;
import com.nimbusds.oauth2.sdk.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

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

    @ExceptionHandler({MessagingException.class, UnsupportedEncodingException.class})
    public ErrorResult messagingExceptionHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("Error", "이메일 생성 에러");
    }

    @ExceptionHandler(MailException.class)
    public ErrorResult mailExceptionHandle(MailException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("Error", "이메일 전송 에러");
    }

//    @ExceptionHandler(BusinessExceptionHandler.class)
//    public ResponseEntity<ErrorResponse> handleCustomException(BusinessExceptionHandler ex) {
//        log.debug("------------------------------------------");
//        final ErrorResponse response = ex.getMessage()
//    }
}
