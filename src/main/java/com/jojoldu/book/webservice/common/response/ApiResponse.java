package com.jojoldu.book.webservice.common.response;

import lombok.Builder;
import lombok.Getter;



@Getter
public class ApiResponse<T> {
    private T result;
    private int resultCode;
    private String resultMsg;

    @Builder

    public ApiResponse(T result, int resultCode, String resultMsg) {
        this.result = result;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
