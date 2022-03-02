package org.comunity.hongga.controller.member;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.comunity.hongga.model.dto.request.member.MemberSignInRequestDTO;
import org.comunity.hongga.model.dto.request.member.MemberSignUpRequestDTO;
import org.comunity.hongga.service.member.MemberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 회원 관련 JPA
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.0.1, 2022.02.15 로그인 서비스 구현
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.15 로그인 서비스 구현
 * @See ""
 * @see <a href=""></a>
 */

@Api(tags = {"회원 관리 관련 API"}) @Slf4j
@RequiredArgsConstructor @RequestMapping(ServiceURIVersion.NOW_VERSION)
@RestController public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    @ApiOperation(value = SwaggerApiInfo.SIGN_UP, notes = "회원 가입 서비스 입니다.")
    @ApiParam(name = "MemberSignUpRequestDTO", value = "이용을 원하는 이용자가 회원 가입을 합니다. \n 필수 : 자기 소개, 프로필 사진을 제외한 모든 항목 \n 비밀번호는 암호화 처리", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.가입 성공 \t 2. Email 중복")})

    @PostMapping("/signup") public ResponseEntity<DefaultResponse> signUp (
            @Valid @RequestBody MemberSignUpRequestDTO signUpRequestDTO) {

        log.info("memberController가 동작 하였습니다!");
        log.info("signUp (@Valid @ResponseBody MemberSignUpRequestDTO signUpRequestDTO)이 호출 되었습니다!");

        log.info("MemberService.signUp을 호출하여 signUpRequestDTO 객체를 전달 하겠습니다!");

        return new ResponseEntity<>(memberServiceImpl.signUp(signUpRequestDTO), HttpStatus.OK);

    } // signUp (@Valid @ResponseBody MemberSignUpRequestDTO signUpRequestDTO) 끝

    @ApiOperation(value = SwaggerApiInfo.SIGN_IN, notes = "로그인 서비스 입니다.")
    @ApiParam(name = "MemberSignUpDTO", value = "이용을 원하는 이용자가 로그인 합니다. \n 필수 : email, Password", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.로그인 성공 \t 2. ID 불일치 \t 3. Password 불일치")})

    @PostMapping("/signin") public ResponseEntity<DefaultResponse> signin (@Valid @RequestBody MemberSignInRequestDTO memberSignInRequestDTO) {

        return new ResponseEntity<>(memberServiceImpl.signIn(memberSignInRequestDTO), HttpStatus.OK);

    } // signin (@Valid @RequestBody MemberSignInRequestDTO memberSignInRequestDTO) 끝

} // class 끝
