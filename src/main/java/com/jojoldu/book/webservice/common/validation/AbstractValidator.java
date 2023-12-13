package com.jojoldu.book.webservice.common.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public abstract class AbstractValidator<T> implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            doValidate((T) target, errors);
        } catch (RuntimeException e) {
            log.error("중복 에러 검증", e);
            throw e;
        }
    }

    protected abstract void doValidate(T target, Errors errors);

    /**
     * Validator 인터페이스를 통한 유효성 검증을 중단하는 메소드
     * Validator 인터페이스의 validate메소드에 추가
     */
    public void suspendValidate(Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
    }
}
