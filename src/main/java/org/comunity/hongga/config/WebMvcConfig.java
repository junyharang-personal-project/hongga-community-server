package org.comunity.hongga.config;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.security.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 각 회원 역할에 따른 Intercepter들과 Resolver를 스프링에 인식할 수 있도록 하는 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.1.0, 2022.02.15 HandlerMethodArgumentResolver를 WebMvcConfiguer의 addArgumentResolvers()를 통해 추가 하기 위해 구현
 *    주니하랑, 1.1.1, 2022.03.03 회원 역할 추가로 인한 접근 제한 구문 추가 및 수정
 *    주니하랑, 1.1.2, 2022.03.07 회원 권한 검증 문제로 인한 로직 추가
 *    주니하랑, 1.1.3, 2022.03.07 회원 가입, 로그인에 대해서는 Intercepter를 처리하지 않게 수정
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.3, 2022.03.07 회원 가입, 로그인에 대해서는 Intercepter를 처리하지 않게 수정
 * @See ""
 * @see <a href=""></a>
 */

@Slf4j // @RequiredArgsConstructor
@Configuration public class WebMvcConfig implements WebMvcConfigurer {

//    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
                .addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE");
    } // addCorsMappings(CorsRegistry registry) 끝


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        log.info("WebMvcConfig가 동작하였습니다!");
        log.info("addInterceptors(InterceptorRegistry registry)가 호출 되었습니다!");

        registry.addInterceptor(guestMemberAPIInterCeptor())
                .addPathPatterns(ServiceURIVersion.NOW_VERSION_GUEST+"/**")
                .excludePathPatterns(ServiceURIVersion.NOW_VERSION_MEMBER+"/**");

        registry.addInterceptor(friendMemberAPIInterCeptor())
                .addPathPatterns(ServiceURIVersion.NOW_VERSION_FRIEND+"/**")
                .excludePathPatterns(ServiceURIVersion.NOW_VERSION_MEMBER+"/**");

        registry.addInterceptor(paternalMemberAPIInterCeptor())
                .addPathPatterns(ServiceURIVersion.NOW_VERSION_PATERNAL+"/**")
                .excludePathPatterns(ServiceURIVersion.NOW_VERSION_MEMBER+"/**");

        registry.addInterceptor(maternalMemberAPIInterCeptor())
                .addPathPatterns(ServiceURIVersion.NOW_VERSION_MATERNAL+"/**")
                .excludePathPatterns(ServiceURIVersion.NOW_VERSION_MEMBER+"/**");

        registry.addInterceptor(valentineMemberAPIInterCeptor())
                .addPathPatterns(ServiceURIVersion.NOW_VERSION_VALENTINE+"/**")
                .excludePathPatterns(ServiceURIVersion.NOW_VERSION_MEMBER+"/**");

        registry.addInterceptor(familyMemberAPIInterCeptor())
                .addPathPatterns(ServiceURIVersion.NOW_VERSION_FAMILY+"/**")
                .excludePathPatterns(ServiceURIVersion.NOW_VERSION_MEMBER+"/**");

        registry.addInterceptor(adminMemberAPIInterCeptor())
                .addPathPatterns(ServiceURIVersion.NOW_VERSION_ADMIN+"/**")
                .excludePathPatterns(ServiceURIVersion.NOW_VERSION_MEMBER+"/**");

    } // addInterceptors(InterceptorRegistry registry) 끝

    @Bean public GuestMemberAPIInterCeptor guestMemberAPIInterCeptor() {

        log.info("WebMvcConfig의 guestMemberAPIInterCeptor()가 호출 되었습니다!");

        return new GuestMemberAPIInterCeptor();

    }  // guestMemberAPIInterCeptor() 끝

    @Bean public FriendMemberAPIInterCeptor friendMemberAPIInterCeptor() {

        log.info("WebMvcConfig의 friendMemberAPIInterCeptor()가 호출 되었습니다!");

        return new FriendMemberAPIInterCeptor();

    }  // friendMemberAPIInterCeptor() 끝

    @Bean public PaternalMemberAPIInterCeptor paternalMemberAPIInterCeptor() {

        log.info("WebMvcConfig의 paternalMemberAPIInterCeptor()가 호출 되었습니다!");

        return new PaternalMemberAPIInterCeptor();

    }  // paternalMemberAPIInterCeptor() 끝

    @Bean public MaternalMemberAPIInterCeptor maternalMemberAPIInterCeptor() {

        log.info("WebMvcConfig의 maternalMemberAPIInterCeptor()가 호출 되었습니다!");

        return new MaternalMemberAPIInterCeptor();

    }  // maternalMemberAPIInterCeptor() 끝

    @Bean public ValentineMemberAPIInterCeptor valentineMemberAPIInterCeptor() {

        log.info("WebMvcConfig의 valentineMemberAPIInterCeptor()가 호출 되었습니다!");

        return new ValentineMemberAPIInterCeptor();

    }  // maternalMemberAPIInterCeptor() 끝

    @Bean public FamilyMemberAPIInterCeptor familyMemberAPIInterCeptor() {

        log.info("WebMvcConfig의 familyMemberAPIInterCeptor()가 호출 되었습니다!");

        return new FamilyMemberAPIInterCeptor();

    }  // guestMemberAPIInterCeptor() 끝

    @Bean public ADMINMemberAPIInterCeptor adminMemberAPIInterCeptor() {

        log.info("WebMvcConfig의 adminMemberAPIInterCeptor()가 호출 되었습니다!");

        return new ADMINMemberAPIInterCeptor();

    }  // guestMemberAPIInterCeptor() 끝

    /**
     * HandlerMethodArgumentResolver를 WebMvcConfiguer의 addArgumentResolvers()를 통해 추가 하기 위해 구현
     * @param argumentResolvers -
     * @see ""
     */

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//
//        log.info("WebMvcConfigurer의 구현체 WebMvcConfig의 addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)가 호출 되었습니다!");
//
//        argumentResolvers.add(loginUserArgumentResolver);
//    } // addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) 끝
} // class 끝
