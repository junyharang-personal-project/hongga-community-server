package org.comunity.hongga.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.model.dto.MemberSignUpDTO;
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
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.08 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@RestController @RequestMapping(ServiceURIVersion.NOW_VERSION)
public class MemberController {

    private final MemberService memberService;

    // 회원 가입용 Method
    @PostMapping("/signup") public ResponseEntity<Member> signup(@Valid @RequestBody MemberSignUpDTO memberDTO) {

        log.info("MemberController가 동작 하였습니다!");
        log.info("signup(@Valid @RequestBody MemberDTO memberDTO)가 호출 되었습니다!");

        return ResponseEntity.ok(memberService.signup(memberDTO));

    } // signup(@Valid @RequestBody MemberDTO memberDTO) 끝

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
