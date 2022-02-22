package org.comunity.hongga.model.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 등급 Enum Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.0.1, 2022.02.20 회원 등급 추가
 *    주니하랑, 1.0.2, 2022.02.22 Spring Security 체계에 맞는 문자열 체계 확립
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.2, 2022.02.22 Spring Security 체계에 맞는 문자열 체계 확립
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor
public enum MemberGrade {

    ROLE_GUEST("손님"),
    ROLE_FRIEND("친구"),
    ROLE_PATERNAL("친가"),
    ROLE_MATERNAL("외가"),
    ROLE_VALENTINE("애인"),
    ROLE_FAMILY("가족"),
    ROLE_ADMIN("관리자");

    private String description;

} // enum class 끝
