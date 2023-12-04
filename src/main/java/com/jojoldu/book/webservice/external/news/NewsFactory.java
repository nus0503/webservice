package com.jojoldu.book.webservice.external.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

public class NewsFactory {


    public static News generated(NewsType newsType) {

        if (newsType == NewsType.NAVER) {
            return new NaverNews();
        }
        throw new IllegalStateException("문법에러");
    }
}
