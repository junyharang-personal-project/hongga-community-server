package org.comunity.hongga.model.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.member.Member;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 회원 가입을 위한 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.09 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.09 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor
public class MemberSignUpDTO {

    @Email @Size(min = 8, max = 50, message = "Email 주소는 8글자 이상 50글자 이하여야 합니다!")
    @NotEmpty private String email;

    @Size(min = 8, message = "암호는 8글자 이상이여야 합니다!")
    @NotEmpty private String password;

    @Size(min = 3, max = 4, message = "이름은 3글자 이상 4글자 이하여야 합니다!")
    @NotEmpty private String name;

    @Size(min = 4, max = 20, message = "별명은 4글자 이상 20글자 이하여야 합니다!")
    @NotEmpty private String nickname;

    @Size(min = 11, max = 13, message = "핸드폰 번호는 필수 입력 사항 입니다!")
    @NotEmpty private String phoneNumber;

    private String authorityName;

    @Lob // 길이 65,535 byte
    private String aboutMe;

    @Builder public Member toEntity() {

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .aboutMe(aboutMe)
                .build();
    } // toEntity() 끝
} // class 끝
