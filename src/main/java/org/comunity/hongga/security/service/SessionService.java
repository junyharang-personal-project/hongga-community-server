package org.comunity.hongga.security.service;


import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ResponseCode;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberRole;
import org.comunity.hongga.repository.member.MemberRepository;
import org.comunity.hongga.security.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * JWT 재 발행을 위한 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 022.02.16 최초 작성
 *    주니하랑, 1.0.1, 2022.03.02 응답 코드 구체화로 인한 return문 수정
 *    주니하랑, 1.1.0, 2022.03.02 회원 역할 관련 Enum Class 이름 변경으로 인한 수정
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.0, 2022.03.02 회원 역할 관련 Enum Class 이름 변경으로 인한 수정
 * @See ""
 * @see <a href=""></a>
 */

@Slf4j @RequiredArgsConstructor
@Service public class SessionService {

    private final MemberRepository memberRepository;

    public Boolean refreshTokenCheck(Long memberNo, String refreshToken) {

        log.info("SessionService가 동작 하였습니다!");
        log.info("refreshTokenCheck(Long id, String refreshToken)이 호출되어 요청으로 들어 온 이용자의 refreshToken 검증을 시작하겠습니다!");

        log.info("Access Token 재 발행을 요청한 이용자의 고유번호가 DB에 있는지 조회 하겠습니다!");
        Optional<Member> requestMemberInfo = memberRepository.findById(memberNo);

        return requestMemberInfo.map(member -> {

            log.info("DB에 이용자 Token과 요청으로 들어온 refreshToken 값 비교하여 같은지 검증 하겠습니다!");

            if (member.getToken().equals(refreshToken)) {   /* DB에 이용자 Token과 요청으로 들어온 refreshToken 값 비교하여 같다면? */

                log.info("내용이 같아 참을 반환 하겠습니다!");

                return true;

            } else { /* 같지 않다면? */

                log.info("내용이 달라 거짓을 반환 하겠습니다!");

                return false;

            } // if문 끝
        }).orElse(false);

    } // refreshTokenCheck(Long id, String refreshToken) 끝

    public Map<String, MemberRole> memberRoleCheck(Long memberNo) {

        log.info("SessionService가 동작 하였습니다!");
        log.info("memberRoleCheck(Long memberNo)이 호출되어 요청으로 들어 온 이용자의 등급(인가) 검증을 시작하겠습니다!");

        HashMap<String, MemberRole> result = new HashMap<>();
        result.put("permission", null);

        log.info("DB에서 요청 이용자의 고유 번호가 같은 것이 있는지 조회 하겠습니다!");

        Optional<Member> requestMemberInfo = memberRepository.findById(memberNo);

        log.info("조회된 이용자의 고유 번호가 Null이 아닌지 검증하겠습니다!");

        requestMemberInfo.ifPresent(member -> {

            log.info("요청 이용자의 정보가 조회 되었습니다! 해당 이용자의 권한을 Map에 넣겠습니다!");

            result.replace("permission", member.getRole());

        });

        log.info("반환 값으로 Map<String, MemberGrade>을 반환 하겠습니다!");

        return result;

    } // memberGradeCheck(Long memberNo) 끝

    public DefaultResponse replaceToken(HttpServletRequest request) {

        log.info("SessionService가 동작 하였습니다!");
        log.info("replaceToken(HttpServletRequest request)이 호출되어 요청으로 들어 온 이용자에게 Token 재 발행 과정을 시작 하겠습니다!");

        log.info("먼저 요청 Header의 Authorization 값을 Parsing 하겠습니다!");
        String requestHeaderValue = request.getHeader("Authorization");

        log.info("Authorization Null인지 검증 하겠습니다!");

        if (requestHeaderValue == null) {

            log.info("Authorization 값이 Null 입니다!");

            return DefaultResponse.response(ResponseCode.UnauthorisedAPI.getCode(), ResponseCode.UnauthorisedAPI.getMessageKo(), ResponseCode.UnauthorisedAPI.getMessageEn());

        } // if (requestHeaderValue == null) 끝

        log.info("요청 헤더의 \"Bearer \" 부터 헤더 길이만큼의 문자열을 잘라 jwt 값만 추출 하겠습니다!");
        String jwt = requestHeaderValue.substring("Bearer ".length());

        log.info("요청 JWT의 내용들을 Parsing 하겠습니다!");
        Claims claims = JwtUtil.getClaims(jwt);

        log.info("JWT의 claims값이 Null인지 검증 하겠습니다!");

        if (claims == null) {

            log.info("요청으로 들어온 JWT의 claims 값이 Null 입니다! Token 불일치로 401 Error 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.TokenError.getCode(), ResponseCode.TokenError.getMessageKo(), ResponseCode.TokenError.getMessageEn());

        } // if (claims == null) 끝

        log.info("요청으로 들어 온 JWT claims(Body)에 token 이름과 이용자 등급을 Parsing 하겠습니다!");

        String tokenName = claims.get("token_name", String.class);

        String memberRole = claims.get("member_role", String.class);

        log.info("요청으로 들어온 Token의 이름이 \"REFRESH_TOKEN_NAME\" 인지 검증 하겠습니다!");

        if (tokenName.equals(JwtUtil.REFRESH_TOKEN_NAME)) {

            log.info("요청으로 들어온 Token의 이름이 \"REFRESH_TOKEN_NAME\"이며, Token claims(Body)에 회원 고유 번호 (member_no)을 Parsing 하겠습니다!");

            Long memberNo = claims.get("member_no", Long.class);

            log.info("refreshTokenCheck(memberNo, jwt)을 호출하여 Refresh Token 유효성 검사를 실시 하겠습니다!");

            Boolean tokenCheck = refreshTokenCheck(memberNo, jwt);

            if (tokenCheck) {

                log.info("Refresh Token 유효성 검사가 정상적으로 마쳤습니다! (참 반환)");

                log.info("AccessToken을 재 발급 하기 위해 변수를 선언 하겠습니다!");
                String accessToken;

                log.info("요청 이용자의 등급을 확인 하겠습니다!");
                if (memberRole.equals(MemberRole.ROLE_ADMIN.getKey())) {

                    log.info("요청 이용자의 등급이 관리자 입니다!");
                    log.info("JwtUtil.createAccessToken(memberNo, MemberGrade.ADMIN)을 호출하여 accessToken을 발급 받겠습니다!");

                    accessToken = JwtUtil.createAccessToken(memberNo, MemberRole.ROLE_ADMIN);

                } else if (memberRole.equals(MemberRole.ROLE_FAMILY.getKey())) {

                    log.info("요청 이용자의 등급이 가족 입니다!");
                    log.info("JwtUtil.createAccessToken(memberNo, MemberGrade.FAMILY)을 호출하여 accessToken을 발급 받겠습니다!");

                    accessToken = JwtUtil.createAccessToken(memberNo, MemberRole.ROLE_FAMILY);

                } else if (memberRole.equals(MemberRole.ROLE_GUEST.getKey())) {

                    log.info("요청 이용자의 등급이 손님 입니다!");
                    log.info("JwtUtil.createAccessToken(memberNo, MemberGrade.GUEST)을 호출하여 accessToken을 발급 받겠습니다!");

                    accessToken = JwtUtil.createAccessToken(memberNo, MemberRole.ROLE_GUEST);

                } // if (회원 등급 비교) 끝

                log.info("Token 재 발행이 완료 되었으므로, 200 Code와 함께 재 발행 된 Token을 반환 하겠습니다!");

                return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn());

            } else {

                log.info("Refresh Token 유효성 검사가 실패 하였습니다! (거짓 반환)");
                log.info("Token이 검증 되지 않아 재 발행이 불가하여, 401 Code와 함께 \"Token 불 일치\" message 반환 하겠습니다!");

                return DefaultResponse.response(ResponseCode.TokenError.getCode(), ResponseCode.TokenError.getMessageKo(), ResponseCode.TokenError.getMessageEn());

            } // if - else (tokenCheck) 끝

        } else {

            log.info("요청으로 들어온 Token의 이름이 \"REFRESH_TOKEN_NAME\"가 아니기 때문에, T401 Code와 함께 \"Token 불 일치\" message 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.TokenError.getCode(), ResponseCode.TokenError.getMessageKo(), ResponseCode.TokenError.getMessageEn());

        } // if - else (tokenName.equals(JwtUtil.REFRESH_TOKEN_NAME)) 끝
    } // replaceToken(HttpServletRequest request) 끝
} // class 끝
