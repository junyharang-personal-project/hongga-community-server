package org.comunity.hongga.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.comunity.hongga.model.dto.request.member.LoginDTO;
import org.comunity.hongga.security.auth.config.jwt.TokenProvider;
import org.comunity.hongga.security.dto.TokenDTO;
import org.comunity.hongga.security.filter.jwt.JwtFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 인증을 위한 Controller
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.1, 2022.02.08 최초 작성
 *    주니하랑, 1.0.1, 2022.02.14 Swagger 관련 내용 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.14 Swagger 관련 내용 작성
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j @Api(tags = {"로그인 관련 서비스"}) @ApiOperation(value = SwaggerApiInfo.AUTHORIZE)
@RestController @RequestMapping(ServiceURIVersion.NOW_VERSION) public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @ApiOperation(value = SwaggerApiInfo.SIGN_IN, notes = "로그인 처리를 위한 서비스 입니다.")
    @ApiParam(name = "LoginDTO", value = "로그인 Email, Password를 담은 DTO 객체", readOnly = true)

    // loginDTO 안에는 이용자 email과 password 존재
    @PostMapping("/authenticate") public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody LoginDTO loginDTO) {

        // TODO AuthController authorize(@Valid @RequestBody LoginDTO loginDTO) 아래 내용 서비스로 따로 빼서 옯겨야 함.
        log.info("AuthController가 호출 되었습니다!");
        log.info(ServiceURIVersion.NOW_VERSION+"/authenticate 가 요청 되어 authorize(@Valid @RequestBody LoginDTO loginDTO)가 동작 하였습니다!");

        log.info("LoginDTO의 email, password를 매개변수로 받아 UsernamePasswordAuthenticationToken 생성 됩니다!");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        log.info("authenticationToken을 이용하여 Authentication 객체를 생성하는데, authenticate Method 실행 시 loadUserByUsername Method 호출합니다.");
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        log.info("Authentication 객체를 SecurityContext에 저장하겠습니다!");
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        log.info("Authentication 객체를 createToken Method를 통해 JWT Token 생성하겠습니다!");
        String jwt = tokenProvider.createToken(authenticationToken);

        HttpHeaders httpHeaders = new HttpHeaders();

        log.info("JWT는 Response Header에도 넣어주겠습니다!");
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        log.info("TokenDTO를 이용하여 Response Body에도 넣어 반환 하겠습니다!");
        return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);

    } // authorize(@Valid @RequestBody LoginDTO loginDTO) 끝

} // class 끝끝