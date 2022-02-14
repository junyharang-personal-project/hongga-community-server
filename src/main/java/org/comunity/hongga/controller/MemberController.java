package org.comunity.hongga.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.comunity.hongga.model.dto.member.MemberSignUpDTO;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 회원 가입 등을 위한 Controller
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 *    주니하랑, 1.0.1, 2022.02.14 Swagger 관련 내용 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.14 Swagger 관련 내용 작성
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j @Api(tags = {"회원 가입 관련 서비스"}) @ApiOperation(value = SwaggerApiInfo.SIGN_UP)
@RestController @RequestMapping(ServiceURIVersion.NOW_VERSION)
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = SwaggerApiInfo.SIGN_IN, notes = "회원 가입을 위한 서비스 입니다.")
    @ApiParam(name = "MemberSignUpDTO", value = "회원 가입 시 입력 해야 될 내용을 담은 DTO 객체", readOnly = true)
    // 회원 가입용 Method
    @PostMapping("/signup") public ResponseEntity<Member> signup(@Valid @RequestBody MemberSignUpDTO membersignUpDTO) {

        log.info("MemberController가 동작 하였습니다!");
        log.info("signup(@Valid @RequestBody MemberDTO memberDTO)가 호출 되었습니다!");

        return ResponseEntity.ok(memberService.signup(membersignUpDTO));

    } // signup(@Valid @RequestBody MemberDTO memberDTO) 끝

    /*
    ================================TEST==========================================
     */

    @GetMapping("/member") @PreAuthorize("hasAnyRole('FAMILY', 'ADMIN')")
    public ResponseEntity<Member> getServiceMemberInfo() {  /* PreAuthorize를 통해 가족과 관리자 권한 모두를 호출 할 수 있도록 허용  */

        log.info("MemberController가 동작 하였습니다!");
        log.info("getServiceMemberInfo()가 호출 되었습니다!");

        return ResponseEntity.ok(memberService.getServiceMemberWithGrade().get());

    } // getServiceMemberInfo() 끝

    @GetMapping("/member/{email}") @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Member> getMemberDetailInfo(@PathVariable String email) {

        log.info("MemberController가 동작 하였습니다!");
        log.info("getMemberDetailInfo(@PathVariable Long memberId)가 호출 되었습니다!");

        return ResponseEntity.ok(memberService.getServiceMemberWithGrade(email).get());

    } // getMemberDetailInfo(@PathVariable Long memberId) 끝
} // class 끝
