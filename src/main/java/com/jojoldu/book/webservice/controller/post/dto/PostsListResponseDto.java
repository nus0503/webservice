package com.jojoldu.book.webservice.controller.post.dto;

import com.jojoldu.book.webservice.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;

    private int viewCount;
    private String modifiedDate;

    public PostsListResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.author = posts.getAuthor();
        this.viewCount = posts.getViewCount();
        this.modifiedDate = posts.getModifiedDate();
    }
}
