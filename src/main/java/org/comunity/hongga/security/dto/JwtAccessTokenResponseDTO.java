package org.comunity.hongga.security.dto;

import lombok.Getter;

/**
 * Access Token 재 발급 관련 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.03.07 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.03.07 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter
public class JwtAccessTokenResponseDTO {

    private String message;

    private String accessToken;

    public JwtAccessTokenResponseDTO (String message, String accessToken) {
        this.message = message;
        this.accessToken = accessToken;
    } // JwtAccessTokenResponseDTO (String message, String accessToken) 끝

} // class 끝
