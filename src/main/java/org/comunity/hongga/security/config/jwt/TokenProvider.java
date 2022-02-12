package org.comunity.hongga.security.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * JWT 사용을 위한 Util Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.08 최초 작성
 * @See "인프런 - "
 * @see <a href=""></a>
 */

@Slf4j
@Component public class TokenProvider implements InitializingBean {

    private static final String AUTHOTITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInmilliseconds;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInmilliseconds) {

        log.info("TokenProvider Class 가 호출 되었습니다!");
        log.info("TokenProvider() 이 동작 하였습니다!");

        this.secret = secret;
        this.tokenValidityInmilliseconds = tokenValidityInmilliseconds * 1000;

    } // 생성자 끝

    @Override
    public void afterPropertiesSet() throws Exception {     /* 위에서 주입 받은 secret 값을 Base64 Decode를 통해 key 변수에 저장 */

        log.info("TokenProvider 가 호출 되었습니다!");
        log.info("afterPropertiesSet()이 동작 하였습니다!");

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        this.key = Keys.hmacShaKeyFor(keyBytes);
    } // afterPropertiesSet() 끝

    public String createToken(Authentication authentication) {  /* JWT 생성 Method */

        log.info("TokenProvider 가 호출 되었습니다!");
        log.info("createToken(Authentication authentication)이 동작 하였습니다!");

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        // application.yml에서 설정했던 만료 시간을 설정하고, Token 생성
        long now = new Date().getTime();

        Date validityJWTExpireTime = new Date(now + this.tokenValidityInmilliseconds);

        // jwt 생성 뒤 반환
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHOTITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validityJWTExpireTime)
                .compact();

    } // createToken(Authentication authentication) 끝

    public Authentication getAuthentication(String token) {     /* Token에 담긴 정보를 이용 Authentication 객체 반환 Method */

        log.info("TokenProvider 가 호출 되었습니다!");
        log.info("Authentication getAuthentication(String token)이 동작 하였습니다!");

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHOTITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }   // tAuthentication(String token) 끝

    public boolean validateToken(String token) {    /* JWT 유효성 검증 Method */

        log.info("TokenProvider 가 호출 되었습니다!");
        log.info("validateToken(String token)이 동작 하였습니다!");

        // Token을 매개변수로 받아 parserBuilder를 통해 parsing 하고, 발생하는 Exception을 잡아 문제가 있으면 False, 없으면 true 반환
        try {

            log.info("Token을 매개변수로 받아 parserBuilder를 통해 parsing하겠습니다!");

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            log.error("TokenProvider에서 JWT 검증을 하는데 문제가 발생 하였습니다!");
            log.error("catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e)가 동작 하였습니다!");

            e.printStackTrace();
            log.error("문제 내용 : " + e.getMessage());
            log.error("validateToken(String token) 동작! 잘못 된 JWT 서명으로 확인 됩니다!");

        } catch (ExpiredJwtException e) {

            log.error("TokenProvider에서 JWT 검증을 하는데 문제가 발생 하였습니다!");
            log.error("catch (ExpiredJwtException e)가 동작 하였습니다!");

            e.printStackTrace();
            log.error("문제 내용 : " + e.getMessage());
            log.error("validateToken(String token) 동작! 만료 된 JWT로 확인 됩니다!");

        } catch (UnsupportedJwtException e) {

            log.error("TokenProvider에서 JWT 검증을 하는데 문제가 발생 하였습니다!");
            log.error("catch (UnsupportedJwtException e)가 동작 하였습니다!");

            e.printStackTrace();
            log.error("문제 내용 : " + e.getMessage());
            log.error("validateToken(String token) 동작! 지원되지 않는 JWT로 확인 됩니다!");

        } catch (IllegalArgumentException e) {

            log.error("TokenProvider에서 JWT 검증을 하는데 문제가 발생 하였습니다!");
            log.error("catch (IllegalArgumentException e)가 동작 하였습니다!");

            e.printStackTrace();
            log.error("문제 내용 : " + e.getMessage());
            log.error("validateToken(String token) 동작! 잘못 된 JWT로 확인 됩니다!");
        } // try-catch문 끝

        return false;
    } // validateToken(String token) 끝

} // class 끝
