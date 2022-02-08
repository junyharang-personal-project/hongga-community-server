package org.comunity.hongga.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원 인증 관련 Data 변환 Class
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

@Slf4j @Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class LoginDTO {

    @NotNull
    @Size(min = 4) private String email;
    @NotNull @Size(min = 8) private String password;

} // class 끝
