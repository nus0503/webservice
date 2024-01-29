package com.jojoldu.book.webservice.external.news;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class NewsTypeTest {

    @Test
    public void 스트링으로_이넘클래스찾기() {
        String name = "naver";

        NewsType newsType = NewsType.of(name);

        log.info("타입은={}", newsType.getClass().getTypeName());
        log.info("이름은={}", newsType.name());
        log.info("이름은={}", newsType.getName());
    }
}