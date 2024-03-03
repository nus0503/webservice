package com.jojoldu.book.webservice.config.exception;

import com.jojoldu.book.webservice.common.codes.ErrorCode;
import lombok.Getter;

/**
 * 에러를 사용하기 위한 구현체
 */
public class BusinessExceptionHandler extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public BusinessExceptionHandler(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessExceptionHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
