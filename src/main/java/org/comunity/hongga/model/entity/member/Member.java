package org.comunity.hongga.model.entity.member;

import lombok.*;
import org.comunity.hongga.model.entity.base.BaseDateTime;

import javax.persistence.*;
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

    private boolean activated;                      // 계정 활성화 여부

    @Enumerated(EnumType.STRING) private MemberGrade grade;

} // class 끝
