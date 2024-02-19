package com.jojoldu.book.webservice.controller.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TempPasswordFormRequestDto {
    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;
}
