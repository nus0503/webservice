package com.jojoldu.book.webservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

    @JsonProperty("title")
    private String title;
    @JsonProperty("link")

    private String link;
    @JsonProperty("description")

    private String description;
}
