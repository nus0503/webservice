package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.config.auth.LoginUser;
import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.webservice.service.posts.PostsService;
import com.jojoldu.book.webservice.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class PostIndexController {

    private final PostsService postsService;

    @GetMapping("/posts/read/{id}")
    public String read(@PathVariable Long id, @LoginUser SessionUser user, Model model) {
        PostsResponseDto responseDto = postsService.findById(id);

        if (user != null) {
            model.addAttribute("user", user.getEmail());

            if (responseDto.getUser().getId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }
        }
        postsService.updateViewCount(id);
        model.addAttribute("posts", responseDto);
        return "posts-read";
    }
}
