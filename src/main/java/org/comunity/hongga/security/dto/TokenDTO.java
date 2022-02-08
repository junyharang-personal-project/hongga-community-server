package org.comunity.hongga.security.dto;

import lombok.*;

/**
 * JWT 관련 Data 변환 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.08 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class TokenDTO {

    private String token;

} // class 끝
