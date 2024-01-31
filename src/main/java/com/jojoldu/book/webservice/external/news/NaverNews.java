package com.jojoldu.book.webservice.external.news;

import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NaverNews extends News{

//    @Value("${client.id}")
    private String clientId = "t4VkOvf3qDA7ceFUTgut";

//    @Value("${client.secret}")
    private String clientSecret = "KAEL6tLa8O";


    @Override
    RequestEntity<Void> getNewsDetail(String query) {

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
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        return request;
//        NaverNewsSearchDto naverNewsSearchDto = this.exchangeAsList(request, NaverNewsSearchDto.class);

//        return null;
    }

//    public <T> T exchangeAsList(RequestEntity<?> requestEntity, Class<T> responseType) {
//        return restTemplate.exchange(requestEntity, responseType).getBody();
//
//    }
}
