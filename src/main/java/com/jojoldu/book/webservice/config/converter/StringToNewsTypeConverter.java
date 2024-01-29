package com.jojoldu.book.webservice.config.converter;

import com.jojoldu.book.webservice.external.news.NewsType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
@Slf4j
public class StringToNewsTypeConverter implements Converter<String, NewsType> {
//    private Class<T> enumClass;

//    public StringToNewsTypeConverter(Class<T> enumClass) {
//        this.enumClass = enumClass;
//    }

    @Override
    public NewsType convert(String source) {
        log.info("convert source={}", source);
        return NewsType.valueOf(source.toUpperCase());
    }
}
