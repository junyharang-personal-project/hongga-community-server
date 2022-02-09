package org.comunity.hongga.model.entity.member;

import lombok.*;
import org.comunity.hongga.model.entity.base.BaseDateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
@Entity public class Member extends BaseDateTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 50, unique = true) private String email;
    @Column(length = 4) private String name;
    private String password;
    @Column(length = 10) private String nickname;

    private String phoneNumber;

    private boolean activated;                      // 계정 활성화 여부

    // GUEST, FAMILY, ADMIN
    private String rolse;

    // 자기 소개
    @Lob // 길이 65,535 byte
    private String aboutMe;

    @ManyToMany @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

} // class 끝
