package org.comunity.hongga.security.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security 설정
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

@Slf4j
@EnableWebSecurity
@Configuration public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String secret;

    @Bean JwtUtil jwtUtil() { return new JwtUtil(secret); }

    // TODO - Password 암호화 처리 필요

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        log.info("WebSecurityConfigurerAdapter를 상속 받은 SpringSecurityConfig가 동작 하였습니다!");
        log.info("configure(HttpSecurity http)가 호출 되었습니다!");

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");    // 해당 URL Origin에 대해 CORS 허용
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);      // 모든 HTTP Method 허용
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);      // 모든 요청 Header 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        http.formLogin().disable()
                .csrf().disable()
                .cors().configurationSource(source);

    } // configure(HttpSecurity http) 끝
} // class 끝




