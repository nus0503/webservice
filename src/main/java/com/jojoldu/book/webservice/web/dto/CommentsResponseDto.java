package com.jojoldu.book.webservice.web.dto;

import com.jojoldu.book.webservice.domain.comments.Comments;
import lombok.Getter;

@Getter
public class CommentsResponseDto {

    private Long id;
    private String comment;
    private String createdDate;
    private String modifiedDate;
    private String username;
    private Long postsId;
    private Long userId;

    public CommentsResponseDto(Comments comments) {
        this.id = comments.getId();
        this.comment = comments.getComment();
        this.createdDate = comments.getCreatedDate();
        this.modifiedDate = comments.getModifiedDate();
        this.username = comments.getUser().getName();
        this.postsId = comments.getPosts().getId();
        this.userId = comments.getUser().getId();
    }
}
