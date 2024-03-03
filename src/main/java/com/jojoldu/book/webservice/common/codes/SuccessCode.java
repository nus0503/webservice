package com.jojoldu.book.webservice.common.codes;

import lombok.Getter;

@Getter
public enum SuccessCode {

    SELECT_SUCCESS(200, "200", "SELECT SUCCESS"),
    DELETE_SUCCESS(200, "200", "DELETE SUCCESS"),
    INSERT_SUCCESS(201, "201", "INSERT SUCCESS"),
    UPDATE_SUCCESS(204, "204", "UPDATE SUCCESS");

    // 성공 코드의 '코드 상태'를 반환한다.
    private final int status;

    // 성공 코드의 '코드 값'을 반환한다.
    private final String code;

    // 성공 코드의 '코드 메시지'를 반환한다.s
    private final String message;

    SuccessCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
