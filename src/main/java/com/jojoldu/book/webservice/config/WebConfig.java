package com.jojoldu.book.webservice.config;

import com.jojoldu.book.webservice.config.auth.LoginUserArgumentResolver;
import com.jojoldu.book.webservice.config.converter.StringToNewsTypeConverter;
import com.jojoldu.book.webservice.config.filter.LogFilter;
import com.jojoldu.book.webservice.config.formatter.NumberFormatter;
import com.jojoldu.book.webservice.config.interceptor.SessionInterceptor;
import com.jojoldu.book.webservice.external.news.NewsType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer { //스프링에서 인식될 수 있도록
    private final LoginUserArgumentResolver loginUserArgumentResolver;
    private final SessionInterceptor sessionInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/app/**", "/*.ico", "/error", "/logout", "/images/**", "/attach/**");
    }

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");

        return filterFilterRegistrationBean;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new NumberFormatter());
        registry.addConverter(new StringToNewsTypeConverter());
    }


}
