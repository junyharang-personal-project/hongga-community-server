package org.comunity.hongga.model.dto.response.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.member.MemberRole;

/**
 * 로그인 응답 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.15 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor
public class MemberSignInResponseDTO {

    private String accessToken;
    private String refreshToken;

    private Long memberNo;

    private MemberRole memberRole;

    private String nickname;

    public MemberSignInResponseDTO(String accessToken, String refreshToken, Long memberNo, MemberRole memberRole, String nickname) {

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberNo = memberNo;
        this.memberRole = memberRole;
        this.nickname = nickname;

    } // 생성자 끝

} // class 끝
