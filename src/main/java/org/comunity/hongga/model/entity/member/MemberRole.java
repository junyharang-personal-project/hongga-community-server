package org.comunity.hongga.model.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 회원 등급 Enum Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.0.1, 2022.02.20 회원 등급 추가
 *    주니하랑, 1.0.2, 2022.02.22 Spring Security 체계에 맞는 문자열 체계 확립
 *    주니하랑, 1.0.3, 2022.03.02 Enum Class 이름 변경
 *    주니하랑, 1.1.0, 2022.03.02 OAuth 기능 추가를 위한 Role 정의 변경
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.0, 2022.03.02 OAuth 기능 추가를 위한 Role 정의 변경
 * @See "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.178"
 * @see <a href=""></a>
 */

@Getter @RequiredArgsConstructor
public enum MemberRole {

    ROLE_GUEST("ROLE_GUEST", "손님"),
    ROLE_FRIEND("ROLE_FRIEND", "친구"),
    ROLE_PATERNAL("ROLE_PATERNAL", "친가"),
    ROLE_MATERNAL("ROLE_MATERNAL", "외가"),
    ROLE_VALENTINE("ROLE_VALENTINE", "애인"),
    ROLE_FAMILY("ROLE_FAMILY", "가족"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;

} // enum class 끝
