package org.comunity.hongga.security.dto;

import lombok.Getter;

/**
 * Refresh Token 관련 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.16 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter
public class ReplaceTokenResponseDTO {

    private String accessToken;

    public ReplaceTokenResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    } // 생성자 끝

} // class 끝
