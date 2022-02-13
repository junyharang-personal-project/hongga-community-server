package org.comunity.hongga.model.entity.member;

import lombok.Builder;
import lombok.Getter;
import org.comunity.hongga.model.entity.base.BaseDateTime;

import javax.persistence.*;

/**
 * 소셜 로그인용 회원 DB 관련
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.13 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.13 최초 작성
 * @See "스프링 부트와 AWS로 혼자 구현하는 웹 서비스"
 * @see <a href=""></a>
 */

@Getter
public class OAuthMember extends BaseDateTime {

    //TODO : 소셜 로그인용 회원 객체를 따로 만드는 것이 맞을까?

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String email;
    @Column private String picture;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Role role;

    @Builder public OAuthMember(String name, String email, String picture, Role role) {

        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;

    } // OAuthMember(String name, String email, String picture, Role role) 끝

    public OAuthMember update(String name, String picture) {

        this.name = name;
        this.picture = picture;

        return this;
    } // update(String name, String picture) 끝

    public String getRoleKey() {
        return this.role.getKey();
    } // getRoleKey() 끝

} // class 끝
