package org.comunity.hongga.security.auth.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OAuth2 관련 코드 중 반복을 개선하기 위한 Annotaion
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.03.02 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.03.02 최초 작성
 * @See "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.195"
 * @see <a href=""></a>
 */

// 이 어노테이션이 생성될 수 있는 위치 지정
// PARAMETER로 지정 했기에 Method의 매개 변수로 선언된 객체에서 사용 가능
//@Target(ElementType.PARAMETER)
//@Retention(RetentionPolicy.RUNTIME)
//public @interface LoginUser {
//} // Annotation 끝
