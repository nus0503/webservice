package com.jojoldu.book.webservice.web.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HelloResponceDtoTest {

    @Test
    public void 롬봄_기능_테스트() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponceDto dto = new HelloResponceDto(name, amount);

        // then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);

    }
}