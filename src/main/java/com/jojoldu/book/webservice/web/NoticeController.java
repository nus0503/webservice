package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.common.PageableRequest;
import com.jojoldu.book.webservice.service.posts.PostsService;
import com.jojoldu.book.webservice.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Controller
public class NoticeController {

    private final PostsService postsService;
    @GetMapping("/notice-list")
    public String NoticeListView(Model model, PageableRequest request) {
        model.addAttribute("posts", postsService.findAll(request));
        return "index";
    }

    @PostConstruct
    public void test() {
        for (int i = 0; i < 100; i++) {
            postsService.save(new PostsSaveRequestDto("title" + i, "content" + i, "a"));
        }
    }
}
