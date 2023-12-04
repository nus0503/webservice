package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.config.RestTemplateConfig;
import com.jojoldu.book.webservice.external.news.News;
import com.jojoldu.book.webservice.external.news.NewsFactory;
import com.jojoldu.book.webservice.external.news.NewsType;
import com.jojoldu.book.webservice.web.dto.NaverNewsSearchDto;
import com.nimbusds.jose.util.StandardCharset;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/server")
@RequiredArgsConstructor
public class Naver {
    private final RestTemplate restTemplate;

    @GetMapping("/naver")
    public NaverNewsSearchDto naver() {
        String query = "개발자";
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(query);
        String encode = StandardCharsets.UTF_8.decode(buffer).toString();

        URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com")
                .path("/v1/search/news.json")
                .queryParam("query", encode)
                .queryParam("sort", "date")
                .encode()
                .build()
                .toUri();



        RequestEntity<Void> request = RequestEntity.get(uri)
                .header("X-Naver-Client-Id", "t4VkOvf3qDA7ceFUTgut")
                .header("X-Naver-Client-Secret", "KAEL6tLa8O")
                .build();

        NaverNewsSearchDto naverNewsSearchDtos = this.exchangeAsList(request, NaverNewsSearchDto.class);
        ResponseEntity<String> result = restTemplate.exchange(request, String.class);
        result.getBody();
        return naverNewsSearchDtos;
    }

    public <T> T exchangeAsList(RequestEntity<?> requestEntity, Class<T> responseType) {
        return restTemplate.exchange(requestEntity, responseType).getBody();

    }


    @GetMapping("/news/{type}")
    public NaverNewsSearchDto test(@PathVariable NewsType newsType, @RequestParam("query") String query) {

        News news = NewsFactory.generated(newsType);
        NaverNewsSearchDto news1 = news.getNews(restTemplate, NaverNewsSearchDto.class, query);
    }




}

Social {
    getId();
    getName();
}

class Google impl{
    String id;
    String name
}

class Kakao {
    String kid;
    String nickname;
}







