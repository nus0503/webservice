package com.jojoldu.book.webservice.controller.comments.dto;

import com.jojoldu.book.webservice.domain.comments.Comments;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import com.jojoldu.book.webservice.domain.posts.Posts;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentsReqeustDto {

    private String comment;
    private User user;
    private Posts posts;

    public Comments toEntity() {
        return new Comments(comment, posts, user);
    }
}
