package org.comunity.hongga.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.entity.member.MemberRole;

import java.security.Key;
import java.util.Date;

/**
 * JWT 설정
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.15 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Slf4j
public class JwtUtil {

    private static Key key;

    // Refresh Token 유효시간 2주 (ms단위)
    public static Long REFRESH_TOKEN_VALID_TIME = 14 * 1440 * 60 * 1000L;

    // Access Token 유효시간 15분
    public static Long ACCESS_TOKEN_VALID_TIME = 15 * 60 * 1000L;

    public static String ACCESS_TOKEN_NAME = "ACCESS TOKEN";
    public static String REFRESH_TOKEN_NAME = "REFRESH TOKEN";

    public JwtUtil(String secret) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    } // JwtUtil(String secret) 끝

    // Access Token 생성 / 발급
    public static String createAccessToken(Long memberNo, MemberRole memberRole) {

        log.info("JwtUtil가 동작하였습니다!");
        log.info("createAccessToken(Long memberId, MemberGrade memberGrade)가 호출 되었습니다!");
        log.info(memberNo+"회원님에게 Access Token을 생성하여 발급 하겠습니다!");

        Date now = new Date();

        log.info("Access Token 발급 시각 : " + now);

        return Jwts.builder()
                .claim("member_no", memberNo)
                .claim("member_role", memberRole)
                .claim("token_name", ACCESS_TOKEN_NAME)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ACCESS_TOKEN_VALID_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    } // createAccessToken(Long memberId, MemberGrade memberGrade) 끝

    /*
    Refresh Token 생성 / 발급
     */
    public static String createRefreshToken(Long memberNo, MemberRole memberRole) {

        log.info("JwtUtil가 동작하였습니다!");
        log.info("createRefreshToken(Long memberId, MemberGrade memberGrade)가 호출 되었습니다!");
        log.info(memberNo+"회원님에게 Refresh Token을 생성하여 발급 하겠습니다!");

        Date now = new Date();

        log.info("Refresh Token 발급 시각 : " + now);

        return Jwts.builder()
                .claim("member_no", memberNo)
                .claim("member_role", memberRole)
                .claim("token_name", REFRESH_TOKEN_NAME)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+REFRESH_TOKEN_VALID_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    } // createRefreshToken(Long memberId, MemberGrade memberGrade) 끝

    // Token 유효성 검사
    public static Claims getClaims(String token) {

        log.info("JwtUtil가 동작하였습니다!");
        log.info("getClaims(String token)가 호출 되었습니다!");

        try {

            log.info(token.toString() + " 의 유효성 검사 및 만료 일시 확인 하겠습니다!");

            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {

            log.error(token.toString() + "의 유효성 검사 및 만료 일시 확인이 실패하였습니다!");

            e.printStackTrace();

            log.error(e.getMessage());

            return null;
        } // try-catch 끝

    } // getClaims(String token) 끝
} // class 끝
