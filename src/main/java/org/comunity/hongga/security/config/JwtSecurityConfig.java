package org.comunity.hongga.security.config;

import org.comunity.hongga.security.config.jwt.TokenProvider;
import org.comunity.hongga.security.filter.jwt.JwtFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JWT 관련 설정 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.0, 2022.02.08 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;

    public JwtSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    } // JwtSecurityConfig(TokenProvider tokenProvider) 끝

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider);

        // Security Logic에 Filter 등록
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    } // configure(HttpSecurity http) 끝
} // class 끝
