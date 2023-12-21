package com.jojoldu.book.webservice.common.advice;

import com.jojoldu.book.webservice.config.auth.LoginUser;
import com.jojoldu.book.webservice.config.auth.PrincipalDetail;
import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addAttribute(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal PrincipalDetail userInfo) {
        if (user != null) {
            model.addAttribute("username", user.getName());
        }

        if (userInfo != null) {
            model.addAttribute("userInfo", userInfo.getUser());
        }
    }
}
