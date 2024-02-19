package com.jojoldu.book.webservice.common.password;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordUtil {

    public static String tempPasswordGenerate() {
        return RandomStringUtils.randomAlphabetic(6);
    }
}
