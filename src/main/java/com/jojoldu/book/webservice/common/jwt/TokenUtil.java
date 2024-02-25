package com.jojoldu.book.webservice.common.jwt;

import com.jojoldu.book.webservice.controller.user.dto.UserFindPasswordDto;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 관련 토큰 util class
 *
 * @author KJS
 * @filename TokenUtil
 * @since 2024.02.24
 */
@Slf4j
@Component
public class TokenUtil {


    public static String jwtSecretKey;

    @Value("${custom.jwt-secret-key}")
    public void setJwtSecretKey(String value) {
        jwtSecretKey = value;
    }

    public static String generateJwtToken(UserFindPasswordDto dto, User User) {

        Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(dto))
                .setSubject(String.valueOf(User.getId()))
                .signWith()
    }

    private static Map<String, Object> createClaims(UserFindPasswordDto dto) {

        HashMap<String, Object> claims = new HashMap<>();

        log.info("username : " + dto.getName());
        log.info("userEmail : " + dto.getEmail());

        claims.put("username", dto.getName());
        claims.put("userEmail", dto.getEmail());

        return claims;
    }

    private static Map<String, Object> createHeader() {

        HashMap<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Key createSignature() {

    }
}
