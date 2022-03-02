package org.comunity.hongga.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.security.auth.config.LoginUserArgumentResolver;
import org.comunity.hongga.security.interceptor.ADMINMemberAPIInterCeptor;
import org.comunity.hongga.security.interceptor.FamilyMemberAPIInterCeptor;
import org.comunity.hongga.security.interceptor.GuestMemberAPIInterCeptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 각 회원 역할에 따른 Intercepter들과 Resolver를 스프링에 인식할 수 있도록 하는 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.1.0, 2022.02.15 HandlerMethodArgumentResolver를 WebMvcConfiguer의 addArgumentResolvers()를 통해 추가 하기 위해 구현
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.0, 2022.02.15 HandlerMethodArgumentResolver를 WebMvcConfiguer의 addArgumentResolvers()를 통해 추가 하기 위해 구현
 * @See ""
 * @see <a href=""></a>
 */

@Slf4j @RequiredArgsConstructor
@Configuration public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

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

    //TODO- 추가된 회원 역할에 따른 InterCeter추가 필요

    /**
     * HandlerMethodArgumentResolver를 WebMvcConfiguer의 addArgumentResolvers()를 통해 추가 하기 위해 구현
     * @param argumentResolvers -
     * @see ""
     */

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        log.info("WebMvcConfigurer의 구현체 WebMvcConfig의 addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)가 호출 되었습니다!");

        argumentResolvers.add(loginUserArgumentResolver);
    } // addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) 끝
} // class 끝
