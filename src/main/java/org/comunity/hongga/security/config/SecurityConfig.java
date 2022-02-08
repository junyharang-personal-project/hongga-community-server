package org.comunity.hongga.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
@EnableWebSecurity @EnableGlobalMethodSecurity(prePostEnabled = true)   // @PreAuthorize를 Method 단위로 추가하기 위하여 선언
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Slf4j @EnableWebSecurity @EnableGlobalMethodSecurity(prePostEnabled = true)    // @PreAuthrize를 Method 단위로 추가하기 위해 사용
    @Configuration
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private final JwtUtil jwtUtil;
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

        public SecurityConfig(JwtUtil jwtUtil, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {

            this.jwtUtil = jwtUtil;
            this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
            this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;

        } // 생성자 끝

        @Bean
        public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); } // passwordEncoder() 끝

        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    // h2-console 하위 모든 요청들과 파피콘 관련 요청은 Spring Security 로직을 수행하지 않도록 설정
                    .ignoring().antMatchers("/h2-console/**","/favicon.ico");
        } // configure(WebSecurity web) 끝

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()   // 토큰 방식을 사용할 땐 꺼 주는게 좋다.
                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)

                    //h2-conlole을 위한 설정
                    .and().headers().frameOptions().sameOrigin()

                    // 세션 방식을 사용하지 않을 것이기 때문에 Stateless로 설정
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and().authorizeRequests()
                    /* /sample/guest 하위 경로는 인증을 한 뒤 이용 가능 */
                    .antMatchers("/sample/guest/**").authenticated()
                    .antMatchers("/sample/family/**").access("hasRole('ROLE_FAMILY') or hasRole('ROLE_ADMIN')")
                    .antMatchers("/sample/admin/**").access("hasRole('ROLE_ADMIN')")

                    /* /api/v1/guest 하위 경로는 인증을 한 뒤 이용 가능 */
                    .antMatchers("/api/v1/guest/**").authenticated()
                    .antMatchers("/api/v1/family/**").access("hasRole('ROLE_FAMILY') or hasRole('ROLE_ADMIN')")
                    .antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
                    // 위의 열거 내용 외 모든 요청은 인증 없이 접근 허용한다.
                    .anyRequest().permitAll()
                    // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig Class에 적용
                    .and().apply(new JwtSecurityConfig(jwtUtil));
        } // configure(HttpSecurity http) 끝

} // class 끝
