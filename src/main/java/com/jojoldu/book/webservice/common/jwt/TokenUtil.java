package com.jojoldu.book.webservice.common.jwt;

import com.jojoldu.book.webservice.controller.user.dto.UserFindPasswordDto;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
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

    /**
     * 사용자 정보 기반으로 토큰 생성 및 반환
     *
     * @param dto : 사용자가 비밀번호를 찾기 위한 정보들
     * @param User : 실제 DB 유저 정보
     * @return 토큰
     */

    public static String generateJwtToken(UserFindPasswordDto dto, User User) {

        return Jwts.builder()
                .setHeader(createHeader()) // Header 구성
                .setClaims(createClaims(dto)) // Payload - Claims 구성
                .setSubject(String.valueOf(User.getId())) // Payload - Subject 구성
                .signWith(SignatureAlgorithm.HS256, createSignature()) // Signature 구성
                .setExpiration(createExpiredDate()) // Expired Date 구성
                .compact();
    }

    /**
     * 토큰 기반으로 사용자 정보 반환
     * @param token
     * @return
     */
    public static String parseTokenToUserInfo(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 유효한 토큰인지 확인
     * @param token : 토큰
     * @return
     */
    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);

            log.info("expireTime : " + claims.getExpiration());
            log.info("userName : " + claims.get("username"));
            log.info("userEmail : " + claims.get("userEmail"));

            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token Expired");
            return false;
        } catch (JwtException e) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException e) {
            log.error("Token is null");
            return false;
        }
    }

    /**
     * Header 내에서 토큰 추출
     * @param header
     * @return
     */
    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    /**
     * 토큰 정보를 기반으로 Claims 정보를 반환하는 메서드
     * @param token
     * @return
     */
    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰의 만료기간 지정 메소드
     * @return
     */
    private static Date createExpiredDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 30);
        return c.getTime();
    }

    /**
     * 사용자 정보 기반으로 클래임을 생성
     * @param dto
     * @return
     */
    private static Map<String, Object> createClaims(UserFindPasswordDto dto) {

        HashMap<String, Object> claims = new HashMap<>();

        log.info("username : " + dto.getName());
        log.info("userEmail : " + dto.getEmail());

        claims.put("username", dto.getName());
        claims.put("userEmail", dto.getEmail());

        return claims;
    }

    /**
     * JWT "헤더"값을 생성
     * @return
     */
    private static Map<String, Object> createHeader() {

        HashMap<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    /**
     * JWT "서명(Signature)"을 발급하는 메소드
     * @return
     */
    private static Key createSignature() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
