package com.jojoldu.book.webservice.service.users;

import com.jojoldu.book.webservice.common.password.PasswordUtil;
import com.jojoldu.book.webservice.domain.oAuthUser.Role;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import com.jojoldu.book.webservice.domain.oAuthUser.UserRepository;
import com.jojoldu.book.webservice.controller.user.dto.AddUserRequest;
import com.jojoldu.book.webservice.controller.user.dto.UserImageDto;
import com.jojoldu.book.webservice.controller.user.dto.UserUpdateDto;
import com.jojoldu.book.webservice.service.mail.TempPasswordMailService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TempPasswordMailService tempPasswordMailService;

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .picture("afrhrgh")
                .role(Role.GUEST)
                .build()).getId();
    }
    public Long saveWithProfile(AddUserRequest dto, UserImageDto imageDto) {
        return userRepository.save(new User(dto.getName(), dto.getEmail(), bCryptPasswordEncoder.encode(dto.getPassword()), "asdasd", Role.GUEST, imageDto.toEntity())).getId();
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
        user.modify(dto.getName(), encodedPassword);
    }

    @Transactional
    public void modifyAsPassword(String email) throws MessagingException, UnsupportedEncodingException {
        String tempPassword = PasswordUtil.tempPasswordGenerate();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        String encodedPassword = bCryptPasswordEncoder.encode(tempPassword);
        user.modifyAsPassword(encodedPassword);
        tempPasswordMailService.sendSimpleMessage(email, tempPassword);
    }

    public UserImageDto findById(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        if (user.getUserImage() == null) {
            return new UserImageDto(new Long(0), "empty.jpg");
        }
        return new UserImageDto(user.getUserImage().getId(),
                user.getUserImage().getFileName(),
                user.getUserImage().getFilePath(),
                user.getUserImage().getFileType(),
                user.getUserImage().getFileSize(),
                user.getUserImage().getUploadUser());
    }
}
