package com.jojoldu.book.webservice.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest
//class HelloControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//    @Test
//    void helloDto가_리턴된다() throws Exception {
//        String name = "hello";
//        int amount = 1000;
//
//
//
//    }
//}