package com.jojoldu.book.webservice.controller.post.dto;

import com.jojoldu.book.webservice.controller.comments.dto.CommentsResponseDto;
import com.jojoldu.book.webservice.domain.posts.Posts;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private int viewCount;
    private String createdDate;
    private Long userId;
    private List<CommentsResponseDto> comments;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.viewCount = entity.getViewCount();
        this.createdDate = entity.getCreatedDate();
        this.userId = entity.getUser().getId();
        this.comments = entity.getComments().stream()
                .map(CommentsResponseDto::new)
                .collect(Collectors.toList());
    }
}
