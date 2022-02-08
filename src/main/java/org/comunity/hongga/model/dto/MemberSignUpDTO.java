package org.comunity.hongga.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberGrade;

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

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MemberSignUpDTO {

    @NotNull @Size(min = 8, message = "Email은 8글자 이상이여야 합니다!") private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @NotNull @Size(min = 8, max = 100, message = "비밀번호는 8글자 이상이여야 합니다!") private String password;

    @NotNull @Size(min = 3, max = 4, message = "이름은 3글자 이상 4글자 이하여야 합니다!") private String name;

    @NotNull private String phoneNumber;

    @NotNull @Size(min = 3, max = 10, message = "닉네임은 3글자 이상 10글자 이하여야 합니다!") private String nickname;

    private String aboutMe;

    @Builder public Member toEntity() {

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .grade(MemberGrade.GUEST)
                .build();
    } // toEntity() 끝

} // class 끝
