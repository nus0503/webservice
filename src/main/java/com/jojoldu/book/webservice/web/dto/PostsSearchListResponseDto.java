package com.jojoldu.book.webservice.web.dto;

import com.jojoldu.book.webservice.domain.posts.Posts;

import java.time.LocalDateTime;

public class PostsSearchListResponseDto {
    private Long id;
    private String title;
    private String author;

    private int viewCount;
    private LocalDateTime modifiedDate;

    public PostsSearchListResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.author = posts.getAuthor();
        this.viewCount = posts.getViewCount();
        this.modifiedDate = posts.getModifiedDate();
    }
}
