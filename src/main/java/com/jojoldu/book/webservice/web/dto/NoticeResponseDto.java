package com.jojoldu.book.webservice.web.dto;

import com.jojoldu.book.webservice.common.Period;
import com.jojoldu.book.webservice.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class NoticeResponseDto {

    private Long id;
    private String title;
    private String author;
    private String modifiedDate;

    public NoticeResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.author = posts.getAuthor();
        this.modifiedDate = posts.getModifiedDate().format(Period.yyyyMMddHHmm);
    }
}
