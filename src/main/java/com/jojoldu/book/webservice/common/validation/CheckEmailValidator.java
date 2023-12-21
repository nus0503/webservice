package com.jojoldu.book.webservice.common.validation;

import com.jojoldu.book.webservice.domain.oAuthUser.UserRepository;
import com.jojoldu.book.webservice.web.dto.AddUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<AddUserRequest>{
    private final UserRepository userRepository;
    @Override
    protected void doValidate(AddUserRequest target, Errors errors) {
        if (userRepository.existsByEmail(target.getEmail())) {
            errors.rejectValue("email", "multi", "이미 사용중인 이메일 입니다.");
        }
    }
}
