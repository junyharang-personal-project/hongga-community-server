package org.comunity.hongga.security.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.comunity.hongga.security.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Access Token 재 발행 Router Class
 * <pre>
 *<b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.16 최초 작성
 * @See ""
 * @see <a href=""></a>
 */
@RequiredArgsConstructor @Slf4j @Api(tags = {"Access Token(JWT) 재발급 API"}) @ApiOperation(value = SwaggerApiInfo.REPLACE_TOKEN)
@RestController @RequestMapping(ServiceURIVersion.NOW_VERSION)
public class ReplaceJWTController {

    private final SessionService sessionService;

    @ApiOperation(value = SwaggerApiInfo.REPLACE_TOKEN, notes = "Access Token(JWT) 재발행 서비스 입니다.")
    @ApiParam(name = "MemberSignUpDTO", value = "가족 간에 사용하는 물건에 대해 사용 설명서를 등록합니다. \n 필수 : Tag를 제외한 모든 항목", readOnly = true)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "1.재 발행 성공"),
            @ApiResponse(code = 401, message = "1. 요청 문제 \t 2. Token 불 일치")})

    @GetMapping("/token") public ResponseEntity<DefaultResponse> replaceToken(HttpServletRequest request) {

        log.info("ReplaceJWTController가 동작 하였습니다!");
        log.info("replaceToken(HttpServletRequest request)이 호출 되었습니다!");

        log.info("Client 요청값에 Token을 Parsing 하겠습니다!");

        DefaultResponse defaultResponse = sessionService.replaceToken(request);

        log.info("Client 요청에 권한이 적절한지 검증하겠습니다!");

        if (defaultResponse.getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {   /* 해당 이용자의 요청 권한이 없다면? */

            log.info("Client 요청 권한이 적절하지 않습니다! 401 Code 반환 하겠습니다!");

            // 권한 없음 Error 반환
            return new ResponseEntity<>(defaultResponse, HttpStatus.UNAUTHORIZED);

        } else { /* 해당 이용자의 요청 권한이 있다면? */

            log.info("Client 요청 권한이 적절하여 200 Code 반환하겠습니다!");

            // 200 Code 반환
            return new ResponseEntity<>(defaultResponse, HttpStatus.OK);

        } // if-else 문 끝
    } // replaceToken(HttpServletRequest request) 끝
} // class 끝
