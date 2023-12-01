package com.jojoldu.book.webservice.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageableRequest {

    private static final int DEFAULT_PAGE_SIZE = 5;

    private int page = 0;
    private final int size = DEFAULT_PAGE_SIZE;

    public Pageable toPageable() {
        return PageRequest.of(page, size, postIdDesc());
    }

    private Sort postIdDesc() {
        return Sort.by(Sort.Direction.DESC, "id");
    }
}
