package org.comunity.hongga.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.model.entity.member.MemberRole;
import org.comunity.hongga.security.service.SessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 메뉴얼 댓글 Router
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.03.06 최초 작성
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.03.06 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Api(tags = "auth 관련 API") @RequiredArgsConstructor @Slf4j
@RequestMapping(ServiceURIVersion.NOW_VERSION)
@RestController public class SessionController {

    private final SessionService sessionService;

    @ApiOperation(value = "회원 역할 확인", notes = "이용자의 이용 권한을 위해 역할을 확인하는 API 입니다!")
    @ApiParam(name = "memberNo", value = "이용자 고유 번호", readOnly = true)

    @GetMapping("/auth") public Map<String, MemberRole> memberRoleCheck (@RequestParam("memberNo") Long memberNo) {

        log.info("SessionController의 memberRoleCheck (@RequestParam(\"memberNo\") Long memberNo)가 호출 되었습니다!");
        log.info("sessionService.memberRoleCheck(memberNo)을 호출 하겠습니다!");

        return sessionService.memberRoleCheck(memberNo);

    } // memberRoleCheck (@RequestParam("memberNo") Long memberNo) 끝

} // class 끝
