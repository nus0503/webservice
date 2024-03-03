//package com.jojoldu.book.webservice.config.filter.token;
//
//import antlr.Token;
//import com.jojoldu.book.webservice.common.codes.ErrorCode;
//import com.jojoldu.book.webservice.common.jwt.AuthConstants;
//import com.jojoldu.book.webservice.common.jwt.TokenUtil;
//import com.jojoldu.book.webservice.config.exception.BusinessExceptionHandler;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.SignatureException;
//import lombok.extern.slf4j.Slf4j;
//import org.json.simple.JSONObject;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//@Slf4j
//@Component
//public class JwtAuthorizationFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        // 토큰이 필요한 API URL에 대해 배열 나열
//        List<String> list = Arrays.asList(
//                "/api/update/password"
//        );
//        //토큰이 필요하지 않은 URL에 경우 다음 필터로
//        String requestURI = request.getRequestURI();
//        if (!list.contains(request.getRequestURI())) {
//            filterChain.doFilter(request, response);
//        }
//        // OPTIONS요청일 경우 다음 필터로
//        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
//            filterChain.doFilter(request, response);
//        }
//
//        // Client에서 API요청 시 Header확인
//        String header = request.getHeader(AuthConstants.AUTH_HEADER);
//        log.debug("[+] header Check: " + header);
//
//        try {
//            //Header 내에 토큰이 존재한 경우
//            if (header != null && !header.equalsIgnoreCase("")) {
//
//                //Header 내에 토큰 추출
//                String token = TokenUtil.getTokenFromHeader(header);
//                //토큰이 유효한 지 체크
//                if (TokenUtil.isValidToken(token)) {
//                    //토큰 기바으로 사용자 이메일 반환
//                    String userEmail = TokenUtil.getUserEmailToken(token);
//                    log.debug("[+] userEmail Check: " + userEmail);
//
//                    if (userEmail != null && !userEmail.equalsIgnoreCase("")) {
//                        // TODO: [STEP6] 실제 DB로 조회를 하여 유효한 사용자 인지 확인(인증)하는 부분이 들어가면 될것 같습니다.
//                        filterChain.doFilter(request, response);
//                    } else {
//                        throw new BusinessExceptionHandler("TOKEN isn`t userEmail", ErrorCode.BUSINESS_EXCEPTION_ERROR);
//                    }
//                } else {
//                    throw new BusinessExceptionHandler("Token isn`t invalid", ErrorCode.BUSINESS_EXCEPTION_ERROR);
//                }
//            } else {
//                throw new BusinessExceptionHandler("TOKEN is null", ErrorCode.BUSINESS_EXCEPTION_ERROR);
//            }
//        } catch (Exception e) {
//            // Token 내에 Exception이 발생 하였을 경우 => 클라이언트에 응답값을 반환하고 종료.
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            PrintWriter writer = response.getWriter();
//            JSONObject jsonObject = jsonResponseWrapper(e);
//            writer.print(jsonObject);
//            writer.flush();
//            writer.close();
//        }
//
//    }
//
//    /**
//     * 토큰 관련 Exception 발생 시 예외 응답값 생성
//     * @param e Exception
//     * @return JSONObject
//     */
//    private JSONObject jsonResponseWrapper(Exception e) {
//
//        String resultMsg = "";
//        // JWT 토큰 만료
//        if (e instanceof ExpiredJwtException) {
//            resultMsg = "TOKEN Expired";
//        }
//        // JWT 허용된 토큰이 아님
//        else if (e instanceof SignatureException) {
//            resultMsg = "TOKEN SignatureException Login";
//        }
//        // JWT 토큰내에서 오류 발생 시
//        else if (e instanceof JwtException) {
//            resultMsg = "TOKEN Parsing JwtException";
//        }
//        // 이외 JTW 토큰내에서 오류 발생
//        else {
//            resultMsg = "OTHER TOKEN ERROR";
//        }
//
//        HashMap<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("status", 401);
//        jsonMap.put("code", "9999");
//        jsonMap.put("message", resultMsg);
//        jsonMap.put("reason", e.getMessage());
//        JSONObject jsonObject = new JSONObject(jsonMap);
//        log.error(resultMsg, e);
//        return jsonObject;
//    }
//}
