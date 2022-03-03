package org.comunity.hongga.security.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.security.auth.config.dto.SessionUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

/**
 * OAuth2 관련 코드 중 반복을 개선하기 위한 Annotaion
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.03.02 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.03.02 최초 작성
 * @See "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.196"
 * @see <a href=""></a>
 */

//@RequiredArgsConstructor @Slf4j
//@Component public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
//
//    private final HttpSession httpSession;
//
//    /**
//     * Controller Method의 특정 매개변수를 지원하는지 판단하기 위해 사용 (매개 변수 Class Type이 SessionUser.class이며, @LoginUser가 붙어 있으면 '참'을 반환)
//     * @param parameter
//     * @return boolean - 조건에 맞는지 여부에 따라 참, 거짓 반환
//     * @see "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.197"
//     */
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        log.info("HandlerMethodArgumentResolver의 구현체 LoginUserArgumentResolver의 supportsParameter(MethodParameter parameter)가 호출 되었습니다!");
//
//        boolean isLoginUserAnnotaion = parameter.getParameterAnnotation(LoginUser.class) != null;
//
//        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
//
//        return isLoginUserAnnotaion && isUserClass;
//    } // supportsParameter(MethodParameter parameter) 끝
//
//    /**
//     * 매개 변수에 전달할 객체 생성
//     * @see "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.198"
//     */
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        log.info("HandlerMethodArgumentResolver의 구현체 LoginUserArgumentResolver의 resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)가 호출 되었습니다!");
//
//        log.info("세션의 객체를 가져와서 반환 하겠습니다!");
//        return httpSession.getAttribute("user");
//    } // resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) 끝
//} // class 끝
