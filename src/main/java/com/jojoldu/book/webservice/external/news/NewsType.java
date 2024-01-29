package com.jojoldu.book.webservice.external.news;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public enum NewsType {

    NAV("naver"),
    KAKAO("kakao");

    private static final Map<String, String> NAME_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(NewsType::getName, NewsType::name)));
    @Getter
    private final String name;

    public static NewsType of(final String name) {
        return NewsType.valueOf(NAME_MAP.get(name));
    }

}
