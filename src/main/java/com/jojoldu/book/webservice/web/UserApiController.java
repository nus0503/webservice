package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.common.file.FileStore;
import com.jojoldu.book.webservice.common.validation.CheckEmailValidator;
import com.jojoldu.book.webservice.service.file.FileService;
import com.jojoldu.book.webservice.service.users.UserService;
import com.jojoldu.book.webservice.web.dto.AddUserRequest;
import com.jojoldu.book.webservice.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;
    private final CheckEmailValidator checkEmailValidator;
    private final AuthenticationManager authenticationManager;

    private final FileService fileService;

    @InitBinder
    public void validatorBinber(WebDataBinder binder) {
        binder.addValidators(checkEmailValidator);
    }

    @PostMapping("/user")
    public String signUp(@ModelAttribute @Valid AddUserRequest userDto, Errors errors, @ModelAttribute MultipartFile imgFile, Model model) throws IOException {

        if (errors.hasErrors()) {
            model.addAttribute("userDto", userDto);

            Map<String, String> validatorResult = userService.validateHandling(errors);
            validatorResult.forEach(
                    (key, value) -> model.addAttribute(key, value)
            );

            return "signup";
        }
        if (imgFile.isEmpty()) {
            userService.save(userDto);
        } else {
            userService.saveWithProfile(userDto, fileService.saveFile(FileStore.getFileDtoFromMultipartFile(imgFile, userDto.getEmail())));
        }
        return "redirect:/loginForm";
    }


    @ResponseBody
    @PutMapping("/user")
    public ResponseEntity<String> modify(@RequestBody UserUpdateDto dto) {
        userService.modify(dto);

        /* 변경된 세션 등록 */
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
