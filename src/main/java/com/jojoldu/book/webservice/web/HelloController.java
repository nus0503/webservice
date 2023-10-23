package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.web.dto.HelloResponceDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponceDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponceDto(name, amount);
    }
}
