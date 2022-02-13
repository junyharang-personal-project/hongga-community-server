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

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no") private Long memberNo;

    @Column(name = "email", length = 50, unique = true, nullable = false) private String email;
    @Column(name = "password", length = 100, nullable = false) private String password;
    @Column(name = "name", length = 4, nullable = false) private String name;
    @Column(name = "nickname", length = 10, nullable = false) private String nickname;

    @Column(name = "phone_number", nullable = false) private String phoneNumber;

    @Column(name = "activated") private boolean activated;                      // 계정 활성화 여부
    @Column private String picture;                                             // 이용자 프로필 사진

    // 자기 소개
    @Lob // 길이 65,535 byte
    @Column(name = "about_me", length = 65535) private String aboutMe;

    @ManyToMany @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_no", referencedColumnName = "member_no")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

} // class 끝
