package org.comunity.hongga.model.entity.member;

import lombok.*;
import org.comunity.hongga.model.entity.base.BaseDateTime;

import javax.persistence.*;
import java.util.*;

/**
 * 회원 DB 관련
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 *    주니하랑, 1.0.1, 2022.02.13 Field 변수 수정 (member_id -> member_no)
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.13 Field 변수 수정 (member_id -> member_no)
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor
@Entity public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Column(length = 50, unique = true, nullable = false) private String email;
    @Column(length = 100, nullable = false) private String password;
    @Column(length = 4, nullable = false) private String name;
    @Column(length = 10, nullable = false) private String nickname;

    @Column(nullable = false) private String phoneNumber;

    private boolean activated;                      // 계정 활성화 여부
    @Column private String picture;                                             // 이용자 프로필 사진

    @Enumerated(EnumType.STRING) private MemberGrade grade;

    private String refreshToken;

    // 자기 소개
    @Lob // 길이 65,535 byte
    @Column(length = 65535) private String aboutMe;

    @Builder public Member(String email, String password, String name, String nickname, String phoneNumber, MemberGrade grade, String picture, String aboutMe) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.picture = picture;
        this.aboutMe = aboutMe;

    } // 생성자 끝

    public void  setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }    // setRefreshToken(String refreshToken) 끝

} // class 끝
