package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.common.validation.CheckEmailValidator;
import com.jojoldu.book.webservice.service.users.UserService;
import com.jojoldu.book.webservice.web.dto.AddUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;
    private final CheckEmailValidator checkEmailValidator;

    @InitBinder
    public void validatorBinber(WebDataBinder binder) {
        binder.addValidators(checkEmailValidator);
    }

    @PostMapping("/user")
    public String signUp(@ModelAttribute @Valid AddUserRequest userDto, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("userDto", userDto);

            Map<String, String> validatorResult = userService.validateHandling(errors);
            validatorResult.forEach(
                    (key, value) -> model.addAttribute(key, value)
            );

            return "signup";
        }
        userService.save(userDto);
        return "redirect:/loginForm";
    }


}
