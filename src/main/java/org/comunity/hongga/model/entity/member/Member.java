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
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.08 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id") private Long memberId;

    @Column(name = "email", length = 50, unique = true) private String email;
    @Column(name = "password", length = 100) private String password;
    @Column(name = "name", length = 4) private String name;
    @Column(name = "nickname", length = 10) private String nickname;
    @Column(name = "phone_number") private String phoneNumber;

    @Column(name = "activated") private boolean activated;                      // 계정 활성화 여부

    // 자기 소개
    @Lob // 길이 65,535 byte
    @Column(name = "about_me", length = 65535) private String aboutMe;

    @ManyToMany @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

} // class 끝
