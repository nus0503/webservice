package com.jojoldu.book.webservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserViewController {

    @GetMapping("/loginForm")
    public String login(@RequestParam(value = "error", required = false)String error,
                        @RequestParam(value = "exception", required = false)String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "loginForm";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
