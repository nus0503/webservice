package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.common.codes.SuccessCode;
import com.jojoldu.book.webservice.common.jwt.AuthConstants;
import com.jojoldu.book.webservice.common.jwt.TokenUtil;
import com.jojoldu.book.webservice.common.response.ApiResponse;
import com.jojoldu.book.webservice.controller.user.dto.UserFindPasswordDto;
import com.jojoldu.book.webservice.controller.user.dto.UserImageDto;
import com.jojoldu.book.webservice.service.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/test")
public class TestController {

    private final UserService userService;

    @PostMapping("generateToken")
    public ResponseEntity<ApiResponse> selectCodeList(@RequestBody UserFindPasswordDto dto) {
        UserImageDto user = userService.findById(dto.getEmail());
        String resultToken = TokenUtil.generateJwtToken(dto, user);

        ApiResponse ar = ApiResponse.builder()
                // BEARER {토큰} 형태로 반환을 해줍니다.
                .result(AuthConstants.TOKEN_TYPE + " " + resultToken)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }
}
