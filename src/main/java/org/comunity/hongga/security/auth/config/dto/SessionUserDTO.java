package org.comunity.hongga.security.auth.config.dto;

import lombok.Getter;
import org.comunity.hongga.model.entity.member.OAuthMember;

import java.io.Serializable;

/**
 * 직렬화 기능을 구현하기 위한 OAuthMember Entity의 DTO
 * <pre>
 * <b>History:</b>
 *  주니하랑, 1.0.0, 2022.02.13 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.13 최초 작성
 * @See "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.187 ~ 188"
 * @see <a href=""></a>
 */

@Getter
public class SessionUserDTO implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUserDTO(OAuthMember member) {

        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();

    } // 생성자 끝
} // class 끝
