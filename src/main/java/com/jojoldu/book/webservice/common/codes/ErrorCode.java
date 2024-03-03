package com.jojoldu.book.webservice.common.codes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [공통 코드] API통신을 위한 에러 코드를 Enum형태로 관리
 * Global Error CodeList : 전역으로 발생하는 에러코드를 관리한다.
 * Custom Error CodeList : 업무 페이지에서 발생하는 에러코드를 관리한다
 * Error Code Constructor : 에러코드를 직접적으로 사용하기 위한 생성자를 구성한다.
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode {

    BUSINESS_EXCEPTION_ERROR(200, "B999", "Business Exception Error"),

    /**
     * ******************************* Custom Error CodeList ***************************************
     */
    // Transaction Insert Error
    INSERT_ERROR(200, "9999", "Insert Transaction Error Exception"),

    // Transaction Update Error
    UPDATE_ERROR(200, "9999", "Update Transaction Error Exception"),

    // Transaction Delete Error
    DELETE_ERROR(200, "9999", "Delete Transaction Error Exception");

    /**
     * ******************************* Error Code Constructor ***************************************
     */

    // 에러 코드의 '코드 상태'을 반환한다.
    private int status;

    // 에러 코드의 '코드간 구분 값'을 반환한다.
    private String divisionCode;

    // 에러 코드의 '코드 메시지'을 반환한다.
    private String message;

    ErrorCode(int status, String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
