package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.config.auth.LoginUser;
import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.webservice.service.comments.CommentsService;
import com.jojoldu.book.webservice.web.dto.CommentsReqeustDto;
import com.jojoldu.book.webservice.web.dto.CommentsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentsApiController {

    private final CommentsService commentsService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity commentSave(@PathVariable Long id, @RequestBody CommentsReqeustDto dto, @LoginUser SessionUser user) {
        return ResponseEntity.ok(commentsService.commentSave(user.getEmail(), id, dto));
    }

    @PutMapping("/posts/{postsId}/comments/{id}")
    public ResponseEntity<Long> update(@PathVariable Long postsId, @PathVariable Long id, @RequestBody CommentsUpdateDto dto) {
        commentsService.update(postsId, id, dto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/posts/{postsId}/comments/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long postsId, @PathVariable Long id) {
        commentsService.delete(postsId, id);
        return ResponseEntity.ok(id);
    }
}
