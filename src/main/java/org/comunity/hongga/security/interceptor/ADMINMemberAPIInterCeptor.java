package org.comunity.hongga.security.interceptor;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.entity.member.MemberRole;
import org.comunity.hongga.security.util.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ADMIN 사용자 접근 권한 및 JWT 검사
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.0.1, 2022.03.03 회원 역할 추가로 인한 접근 제한 구문 추가 및 수정
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.03.03 회원 역할 추가로 인한 접근 제한 구문 추가 및 수정
 * @See ""
 * @see <a href=""></a>
 */

@Slf4j
public class ADMINMemberAPIInterCeptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("ADMINMemberAPIInterCeptor 동작하였습니다!");
        log.info("preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)가 호출 되었습니다!");

        String token = request.getHeader("Authorization");

        if (token == null) {
            log.info("token 값이 null 입니다!");
            response.sendError(401, "요청 에러 입니다!");
        } // if (token == null) 끝

        String jwt = token.substring("Bearer ".length());

        log.info("jwt의 이용자 정보를 가져오겠습니다!");
        Claims claims = JwtUtil.getClaims(jwt);

        if (claims == null) {
            log.error("jwt의 이용자 정보가 null 입니다!");

            response.sendError(401, "Access Token 불일치!");

            return false;
        } // if (claims == null) 끝

        log.info("JWT 내에 Token 이름이 무엇인지 찾겠습니다! 예:) Access Token or Refresh Token");
        String tokenName = claims.get("token_name", String.class);

        log.info("JWT 내에 이용자의 등급을 찾도록 하겠습니다!");
        String memberRole = claims.get("member_role", String.class);

        log.info("이용자의 등급이 ADMIN이 아니라면 접근 제한 하겠습니다!");
        if (
                memberRole.equals(MemberRole.ROLE_GUEST.getKey()) ||
                memberRole.equals(MemberRole.ROLE_FRIEND.getKey()) ||
                memberRole.equals(MemberRole.ROLE_PATERNAL.getKey()) ||
                memberRole.equals(MemberRole.ROLE_MATERNAL.getKey()) ||
                memberRole.equals(MemberRole.ROLE_VALENTINE.getKey()) ||
                memberRole.equals(MemberRole.ROLE_FAMILY.getKey())) {

            log.error("이용자의 등급이 ADMIN이 아닙니다! 접근 제한 하겠습니다!\");");
            response.sendError(403, "접근 권한이 없습니다!");

        } // if 끝

        if (tokenName.equals(JwtUtil.ACCESS_TOKEN_NAME)) {  /* token 이름이 "ACCESS TOKEN" 이라면? */

            log.info("Token의 이름이 ACCESS TOKEN 입니다!");

            return true;

        } else {

            log.error("Token 이름이 ACCESS TOKEN이 아닙니다!");

            return false;
        } // if-else 문 끝
    } // preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 끝
} // class 끝
