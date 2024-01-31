package com.jojoldu.book.webservice.controller.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

    @JsonProperty("title")
    private String title;
    @JsonProperty("link")

    private String link;
    @JsonProperty("description")

    private String description;
}
