package org.comunity.hongga.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class MemberDTO {

    @NotNull @Size(min = 3, max = 50) private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @NotNull @Size(min = 3, max = 100) private String password;

    @NotNull @Size(min = 3, max = 50) private String nickname;

} // class 끝
