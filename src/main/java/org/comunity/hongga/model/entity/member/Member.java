package org.comunity.hongga.model.entity.member;

import lombok.*;
import org.comunity.hongga.model.entity.base.BaseDateTime;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 회원 DB 관련
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 *    주니하랑, 1.0.1, 2022.02.13 Field 변수 수정 (member_id -> member_no)
 *    주니하랑, 1.0.2, 가입일과 수정일을 위해 BaseDateTime 상속
 *    주니하랑, 1.0.3, 자기 소개 작성 가능 길이 변경
 *    주니하랑, 1.0.4, 2022.03.02 Enum Class 이름 변경으로 인한 변수 이름 변경
 *    주니하랑, 1.1.0, 2022.03.02 OAuth 적용을 위한 생성자 추가
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.1.0, 2022.03.02 OAuth 적용을 위한 생성자 추가
 * @See "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.177"
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity @Table(name = "tbl_member") public class Member extends BaseDateTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Email @Column(length = 50, unique = true) @NotBlank @Size(message = "이메일 형식으로 입력해야 합니다!")
    private String email;

    @Column(length = 255) private String password;

    @NotBlank @Column(length = 6) @Size(min = 3, max = 6, message = "이름은 3자리 이상 5자리 이하여야 합니다!")
    private String name;

    @NotBlank @Column(length = 10, nullable = false) @Size(min = 2, max = 9, message = "별명은 2자리 이상 9자리 이하여야 합니다!")
    private String nickname;

    @NotBlank @Column(length = 14) @Size(message = "핸드폰 번호를 입력 해주세요!")
    private String phoneNumber;

    private boolean activated;                                                  // 계정 활성화 여부
    @Column private String picture;                                             // 이용자 프로필 사진

    @Enumerated(EnumType.STRING) @Column(nullable = false) private MemberRole role;

    private String token;

    // 자기 소개
    @Column(length = 200) @Size(message = "자기 소개는 최대 200자까지 작성 가능 합니다!") private String aboutMe;

    @Builder public Member(Long memberNo, String email, String password, String name, String nickname, String phoneNumber, MemberRole role, String picture, String aboutMe, boolean activated) {
        this.memberNo = memberNo;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.picture = picture;
        this.aboutMe = aboutMe;
        this.activated = activated;

    } // Member(Long memberNo, String email, String password, String name, String nickname, String phoneNumber, MemberRole role, String picture, String aboutMe, boolean activated) 끝

    public Member(String name, String email, String picture, MemberRole role) {

        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;

    } // Member(String name, String email, String picture, MemberRole role) 끝

    public Member update(String name, String picture) {

        this.name = name;
        this.picture = picture;

        return this;
    } // update(String name, String picture) 끝

    public String getRoleKey() { return this.role.getKey(); }

    public void  setRefreshToken(String token) { this.token = token; }    // setRefreshToken(String refreshToken) 끝

} // class 끝
