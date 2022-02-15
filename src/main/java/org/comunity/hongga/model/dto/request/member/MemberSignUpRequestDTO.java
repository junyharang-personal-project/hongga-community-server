package org.comunity.hongga.model.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberGrade;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Manual Repository
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

@Getter @NoArgsConstructor @AllArgsConstructor
public class MemberSignUpRequestDTO {

    @Email @NotEmpty private String email;

    @NotEmpty private String password;
    @NotEmpty private String name;

    @Size(min = 4, max = 16, message = "별명은 4글자 이상 16글자 이하여야 합니다!") @NotEmpty
    private String nickname;

    @NotEmpty String phoneNumber;

    String picture;

    String aboutMe;

    boolean activated;                      // 계정 활성화 여부

    @Builder public Member toEntity() {

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .picture(picture)
                .grade(MemberGrade.GUEST)
                .aboutMe(aboutMe)
                .activated(true)
                .build();

    } // toEntity() 끝

} // class 끝
