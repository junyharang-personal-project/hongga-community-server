package org.comunity.hongga.model.entity.member;

import lombok.*;
import org.comunity.hongga.model.entity.base.BaseDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * 회원 DB 관련
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 *    주니하랑, 1.0.1, 2022.02.13 Field 변수 수정 (member_id -> member_no)
 *    주니하랑, 1.0.2, 가입일과 수정일을 위해 BaseDateTime 상속
 *    주니하랑, 1.0.3, 자기 소개 작성 가능 길이 변경
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.3, 자기 소개 작성 가능 길이 변경
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity public class Member extends BaseDateTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Column(length = 50, unique = true, nullable = false) private String email;
    @Column(nullable = false) private String password;
    @Column(length = 6, nullable = false) private String name;
    @Column(length = 10, nullable = false) private String nickname;

    @Column(nullable = false) private String phoneNumber;

    private boolean activated;                                                  // 계정 활성화 여부
    @Column private String picture;                                             // 이용자 프로필 사진

    @Enumerated(EnumType.STRING) private MemberGrade grade;

    private String token;

    // 자기 소개
    @Column(length = 200) @Size(message = "자기 소개는 최대 200자까지 작성 가능 합니다!") private String aboutMe;

    @Builder public Member(String email, String password, String name, String nickname, String phoneNumber, MemberGrade grade, String picture, String aboutMe, boolean activated) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.picture = picture;
        this.aboutMe = aboutMe;
        this.activated = activated;

    } // 생성자 끝

    public void  setRefreshToken(String token) { this.token = token; }    // setRefreshToken(String refreshToken) 끝

} // class 끝
