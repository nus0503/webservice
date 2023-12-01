package com.jojoldu.book.webservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter

@ToString
public class NaverNewsSearchDto {
    private int total;

    @JsonProperty()
    private List<Item> items;
}
