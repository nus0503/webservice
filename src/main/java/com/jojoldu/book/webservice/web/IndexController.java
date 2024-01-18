package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.common.PageableRequest;
import com.jojoldu.book.webservice.config.auth.LoginUser;
import com.jojoldu.book.webservice.config.auth.PrincipalDetail;
import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.webservice.service.posts.PostsService;
import com.jojoldu.book.webservice.web.dto.PostsResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;


@RequiredArgsConstructor
@Slf4j
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession session;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("posts", postsService.findAllDesc());
//        SessionUser user = (SessionUser) httpSession.getAttribute("user"); // getAttribute로 가져오던 세션 정보를 어노테이션 기반으로 개선
//        if (user != null) {
//            model.addAttribute("username", user.getName());
//        }
//        if (userInfo != null) {
//            model.addAttribute("userInfo", userInfo.getUser());
//
//        }


        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}, sessionId={}", name, session.getAttribute(name), session.getId()));
        log.info(session.toString());
        log.info("sessionId={}", session.getId());
        log.info("getMax={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        postsService.updateViewCount(id);
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

    @GetMapping("/posts/search")
    public String search(String keyword, PageableRequest request, Model model) {
        model.addAttribute("searchList", postsService.search(keyword, request));
        return "posts-search";
    }


    @GetMapping("/hello2")
    public String hello(Model model) {
        Form form = new Form();
        form.setNumber(10000);
        form.setLocalDateTime(LocalDateTime.now());
        model.addAttribute("form", form);
        return "formatter-form";
    }

    @PostMapping("/hello2")
    public String hello2(@ModelAttribute Form form) {
        return "formatter-view";
    }

    @Data
    static class Form {
        @NumberFormat(pattern = "###,###")
        private Integer number;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;
    }
}
