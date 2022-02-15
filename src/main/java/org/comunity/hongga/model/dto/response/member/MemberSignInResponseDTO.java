package org.comunity.hongga.model.dto.response.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.dto.request.member.MemberSignInRequestDTO;
import org.comunity.hongga.model.entity.member.MemberGrade;

@Getter @NoArgsConstructor
public class MemberSignInResponseDTO {

    private String accessToken;
    private String refreshToken;

    private Long memberNo;

    private MemberGrade memberGrade;

    private String nickname;

    public MemberSignInResponseDTO(String accessToken, String refreshToken, Long memberNo, MemberGrade memberGrade, String nickname) {

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberNo = memberNo;
        this.memberGrade = memberGrade;
        this.nickname = nickname;

    } // 생성자 끝

} // class 끝
