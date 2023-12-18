package com.jojoldu.book.webservice.service.users;

import com.jojoldu.book.webservice.domain.oAuthUser.Role;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import com.jojoldu.book.webservice.domain.oAuthUser.UserRepository;
import com.jojoldu.book.webservice.web.dto.AddUserRequest;
import com.jojoldu.book.webservice.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .picture("afrhrgh")
                .role(Role.GUEST)
                .build()).getId();
    }

    public Map<String, String> validateHandling(Errors errors) {
        return errors.getFieldErrors().stream()
                .collect(Collectors.toMap(
                    error -> String.format("valid_%s", error.getField()),
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> msg1
                ));
    }

    @Transactional
    public void modify(UserUpdateDto dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        user.modify(encodedPassword);
    }
}
