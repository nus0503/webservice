package com.jojoldu.book.webservice.controller.post;

import com.jojoldu.book.webservice.config.auth.LoginUser;
import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.webservice.service.posts.PostsService;
import com.jojoldu.book.webservice.controller.comments.dto.CommentsResponseDto;
import com.jojoldu.book.webservice.controller.post.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Controller
public class PostIndexController {

    private final PostsService postsService;

    @GetMapping("/posts/read/{id}")
    public String read(@PathVariable Long id, @LoginUser SessionUser user, Model model) {
        PostsResponseDto responseDto = postsService.findById(id);
        List<CommentsResponseDto> comments = responseDto.getComments();

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        if (user != null) {
            model.addAttribute("user", user);

            if (responseDto.getUserId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }
            List<Boolean> isis = new ArrayList<>();
            comments.forEach(comment -> {
                boolean isWriter = comment.getUserId().equals(user.getId());
                log.info("isWriter? : " + isWriter);
                isis.add(isWriter);
                model.addAttribute("isis", isis);
//                log.info("isWriter? : " + isWriter);
//                model.addAttribute("isWriter",isWriter);
            });
        }
        postsService.updateViewCount(id);
        model.addAttribute("posts", responseDto);
        return "posts-read";
    }
}
