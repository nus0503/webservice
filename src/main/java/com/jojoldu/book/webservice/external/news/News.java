package com.jojoldu.book.webservice.external.news;

import org.springframework.http.RequestEntity;

import org.springframework.web.client.RestTemplate;

public abstract class News {
    public <T> T getNews(RestTemplate restTemplate, Class<T> responseType, String query) {
        RequestEntity<Void> requestEntity = getNewsDetail(query);
        return restTemplate.exchange(requestEntity, responseType).getBody();
//        exchangeAsList(requestEntity, responseType)
    }

    abstract RequestEntity<Void> getNewsDetail(String query);

//    public T exchangeAsList(RequestEntity<?> requestEntity, Class<T> responseType) {
//        restTemplate.exchange(requestEntity, responseType);
//    }

}

