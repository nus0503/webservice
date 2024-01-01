package com.jojoldu.book.webservice.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class IndexControllerTest {
//    @Autowired
//    TestRestTemplate testRestTemplate;
//
//    @Test
//    public void 메인페이지_로딩() {
//        //when
//        String body = this.testRestTemplate.getForObject("/", String.class);
//        System.out.println(body.toString());
//
//        //then
//        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
//    }
//}
