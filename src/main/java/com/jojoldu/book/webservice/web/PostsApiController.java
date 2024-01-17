package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.config.auth.LoginUser;
import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.webservice.domain.oAuthUser.Role;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import com.jojoldu.book.webservice.exception.BadRequestException;
import com.jojoldu.book.webservice.service.posts.PostsService;
import com.jojoldu.book.webservice.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.webservice.web.dto.PostsResponseDto;
import com.jojoldu.book.webservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity save(@RequestBody PostsSaveRequestDto requestDto, @LoginUser SessionUser user) {
        return ResponseEntity.ok(postsService.save(requestDto, user.getEmail()));

    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id, @LoginUser SessionUser user) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @ResponseBody
    @GetMapping("/api/posts/{id}")
    public PostsSaveRequestDto aa(@PathVariable("id") String id) {

        if ("ex".equals(id)) {
            throw new RuntimeException("잘못");
        }
        return new PostsSaveRequestDto("a", "aa", "aaa", new User("1", "11", "111", "1111", Role.GUEST));
    }

    @ResponseBody
    @GetMapping("/api/reponse-status-ex")
    public String responseExceptionEx() {
        throw new BadRequestException();
    }
}
