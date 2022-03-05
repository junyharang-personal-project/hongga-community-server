package org.comunity.hongga.security.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.model.entity.member.MemberRole;
import org.comunity.hongga.security.service.CustomOAth2UserService;
import org.comunity.hongga.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security 설정
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 *    주니하랑, 1.1.0, 2022.03.02 소셜 로그인 기능 추가를 위한 내용 추가
 *    주니하랑, 1.1.1, 2022.03.03 @PreAutorize를 사용하여 편리하게 URI 접근 제한을 걸기 위해 @EnableGlobalMethodSecurity 추가
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.1, 2022.03.03 @PreAutorize를 사용하여 편리하게 URI 접근 제한을 걸기 위해 @EnableGlobalMethodSecurity 추가
 * @See "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.180 ~ 181"
 * @See "코드로 배우는 스프링 부트 웹 프로젝트 P.556"
 * @see <a href=""></a>
 */

@Slf4j @RequiredArgsConstructor
@EnableWebSecurity // @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAth2UserService customOAth2UserService;

    @Value("${jwt.secret}")
    private String secret;

    @Bean JwtUtil jwtUtil() { return new JwtUtil(secret); }

    // TODO - Password 암호화 처리 필요

    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); } // passwordEncoder() 끝

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
                .cors().configurationSource(source);


        http
                .formLogin().disable()
                // 세션 대신 토큰 방식을 사용하기 때문에 세션 설정을 stateless로 변
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // H2-console 화면을 사용하기 위해 해당 옵션 disable
                .csrf().disable().headers().frameOptions().disable()
                .and()
                // URL별 권한 관리 설정 Option의 시작점(antMatchers를 사용하기 위해 선언)
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
 //               .antMatchers("/**").permitAll();
                // 각 API 별 접근 허용할 Member Role 지정
                .antMatchers(ServiceURIVersion.NOW_VERSION_FRIEND+"/**")
                    .access("hasRole('ROLE_FRIEND') or hasRole('ROLE_PATERNAL') or hasRole('ROLE_MATERNAL') or hasRole('ROLE_VELENTINE') or hasRole('ROLE_FAMILY') or hasRole('ROLE_ADMIN')")
                .antMatchers(ServiceURIVersion.NOW_VERSION_PATERNAL+"/**")
                    .access("hasRole('ROLE_PATERNAL') or hasRole('ROLE_MATERNAL') or hasRole('ROLE_VELENTINE') or hasRole('ROLE_FAMILY') or hasRole('ROLE_ADMIN')")
                .antMatchers(ServiceURIVersion.NOW_VERSION_MATERNAL+"/**")
                    .access("hasRole('ROLE_MATERNAL') or hasRole('ROLE_VELENTINE') or hasRole('ROLE_FAMILY') or hasRole('ROLE_ADMIN')")
                .antMatchers(ServiceURIVersion.NOW_VERSION_VALENTINE+"/**")
                    .access("hasRole('ROLE_VELENTINE') or hasRole('ROLE_FAMILY') or hasRole('ROLE_ADMIN')")
                .antMatchers(ServiceURIVersion.NOW_VERSION_FAMILY+"/**")
                    .access("hasRole('ROLE_FAMILY') or hasRole('ROLE_ADMIN')")
                .antMatchers(ServiceURIVersion.NOW_VERSION_ADMIN+"/**")
                    .access("hasRole('ROLE_ADMIN')")

                 // 위에 열거한 URI 이외 나머지 URI는 인증 없이 접근 가능
                .anyRequest().permitAll()
                .and()
                // 이용자가 로그아웃을 했을 때 이동할 곳 지정
                .logout().logoutSuccessUrl("/")
                .and()
                // OAth2 로그인 기능 이용을 위해 선언
                .oauth2Login()
                    // OAth2 로그인 성공 뒤 이용자 정보를 가져올 설정을 위해 선언
                    .userInfoEndpoint()
                        // 소셜 로그인 성공 뒤 후속 조치를 진행할 UserService Interface의 구현체를 등록
                        // 소셜 서비스(리소스 서버)에서 이용자 정를 가져온 상태에서 추가로 진행하고자 하는 기능 명시를 위해 사용
                        .userService(customOAth2UserService);

    } // configure(HttpSecurity http) 끝
} // class 끝




