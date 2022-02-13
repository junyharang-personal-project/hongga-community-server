package org.comunity.hongga.model.entity.member;

import lombok.Builder;
import org.comunity.hongga.model.entity.base.BaseDateTime;

import javax.persistence.*;

public class OAuthMember extends BaseDateTime {

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
