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
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.20 회원 등급 추가
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor
public enum MemberGrade {

    GUEST("손님"),
    FRIEND("친구"),
    PATERNAL("친가"),
    MATERNAL("외가"),
    VALENTINE("애인"),
    FAMILY("가족"),
    ADMIN("관리자");

    private String description;

} // enum class 끝
