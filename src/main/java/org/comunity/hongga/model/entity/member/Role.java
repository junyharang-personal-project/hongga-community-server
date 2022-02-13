package org.comunity.hongga.model.entity.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 소셜 로그인용 회원 등급 관련
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.13 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.13 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter @RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    FAMILY("ROLE_FAMILY", "가족");

    private final String key;
    private final String title;

} // enum class 끝
