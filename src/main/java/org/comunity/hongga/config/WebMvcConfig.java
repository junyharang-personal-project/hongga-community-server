package org.comunity.hongga.config;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.security.interceptor.ADMINMemberAPIInterCeptor;
import org.comunity.hongga.security.interceptor.FamilyMemberAPIInterCeptor;
import org.comunity.hongga.security.interceptor.GuestMemberAPIInterCeptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 회원 등급 관리 설정
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
@Configuration public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        log.info("WebMvcConfig가 동작하였습니다!");
        log.info("addInterceptors(InterceptorRegistry registry)가 호출 되었습니다!");

        // WebMvcConfigurer.super.addInterceptors(registry);
    } // addInterceptors(InterceptorRegistry registry) 끝

    @Bean public GuestMemberAPIInterCeptor guestMemberAPIInterCeptor() {

        log.info("WebMvcConfig가 동작하였습니다!");
        log.info("guestMemberAPIInterCeptor()가 호출 되었습니다!");
        return new GuestMemberAPIInterCeptor();

    }  // guestMemberAPIInterCeptor() 끝

    @Bean public FamilyMemberAPIInterCeptor familyMemberAPIInterCeptor() {

        log.info("WebMvcConfig가 동작하였습니다!");
        log.info("familyMemberAPIInterCeptor()가 호출 되었습니다!");
        return new FamilyMemberAPIInterCeptor();

    }  // guestMemberAPIInterCeptor() 끝

    @Bean public ADMINMemberAPIInterCeptor adminMemberAPIInterCeptor() {

        log.info("WebMvcConfig가 동작하였습니다!");
        log.info("adminMemberAPIInterCeptor()가 호출 되었습니다!");
        return new ADMINMemberAPIInterCeptor();

    }  // guestMemberAPIInterCeptor() 끝
} // class 끝
