package org.comunity.hongga.security.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.security.auth.config.jwt.JwtAuthenticationEntryPoint;
import org.comunity.hongga.security.auth.config.jwt.TokenProvider;
import org.comunity.hongga.security.auth.config.jwt.handler.JwtAccessDeniedHandler;
import org.comunity.hongga.security.service.HonggaOAuth2UserDetailsService;
import org.springframework.context.annotation.Bean;
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

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final HonggaOAuth2UserDetailsService honggaOAuth2UserDetailsService;

    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler, HonggaOAuth2UserDetailsService honggaOAuth2UserDetailsService) {

        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;

        this.honggaOAuth2UserDetailsService = honggaOAuth2UserDetailsService;

    } // JWT 이용 생성자 끝


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } // passwordEncoder() 끝

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                // h2-console 하위 모든 요청들과 파피콘 관련 요청은 Spring Security 로직을 수행하지 않도록 설정
                .ignoring().antMatchers("/h2-console/**", "/favicon.ico");
    } // configure(WebSecurity web) 끝

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // 토큰 방식을 사용할 땐 꺼 주는게 좋다.
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                //h2-conlole을 위한 설정
                .and().headers().frameOptions().sameOrigin()

                // 세션 방식을 사용하지 않을 것이기 때문에 Stateless로 설정
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                /* /sample/guest 하위 경로는 인증을 한 뒤 이용 가능 */
                .antMatchers("/sample/guest/**").permitAll()
                .antMatchers("/sample/family/**").access("hasRole('ROLE_FAMILY') or hasRole('ROLE_ADMIN')")
                .antMatchers("/sample/admin/**").access("hasRole('ROLE_ADMIN')")

                /* /api/v1/guest 하위 경로는 인증을 한 뒤 이용 가능 */
                .antMatchers("/api/v1/guest/**").permitAll()
                .antMatchers("/api/v1/family/**").access("hasRole('ROLE_FAMILY') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
                // 위의 열거 내용 외 모든 요청은 인증 없이 접근 허용한다.
                .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and().apply(new JwtSecurityConfig(tokenProvider))
                .and().oauth2Login().userInfoEndpoint().userService(honggaOAuth2UserDetailsService);
                // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig Class에 적용


    } // configure(HttpSecurity http) 끝
} // class 끝

