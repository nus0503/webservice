package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.config.auth.LoginUser;
import com.jojoldu.book.webservice.config.auth.PrincipalDetail;
import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.webservice.domain.user.Users;
import com.jojoldu.book.webservice.service.posts.PostsService;
import com.jojoldu.book.webservice.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal PrincipalDetail userInfo) {
        model.addAttribute("posts", postsService.findAllDesc());
//        SessionUser user = (SessionUser) httpSession.getAttribute("user"); // getAttribute로 가져오던 세션 정보를 어노테이션 기반으로 개선
        if (user != null) {
            model.addAttribute("username", user.getName());
        }
        if (userInfo != null) {
            model.addAttribute("userInfo", userInfo.getUser());

        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
