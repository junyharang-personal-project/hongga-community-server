package org.comunity.hongga.security.auth.config.dto;

import lombok.Builder;
import lombok.Getter;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberRole;

import java.util.Map;

/**
 * 이용자 정보를 받아 Entity를 생성하는 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.03.02 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.03.02 최초 작성
 * @See "https://seokr.tistory.com/811"
 * @see <a href=""></a>
 */

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;

    } // OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) 끝

    /**
     * OAuth2User에서 반환하는 이용자 정보는 Map이므로, 값 하나하나 반환하기 위해 사용
     * @param registrationId -
     * @param userNameAttributeName -
     * @param attributes -
     * @return OAuthAttributes -
     * @see "스프링 부트와 AWS로 혼자 구현하는 웹 서비스"
     */

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    } // of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) 끝


    /**
     * Member Entity 생성을 위한 Method
     * OAuthAttributes에서 Entity를 생성하는 시점은 처음 가입할 때 이며, 가입 할 때 기본 권한을 GUEST로 주기 위해 role Builder Value를 Role.GUEST로 사용
     * @return Member - 소셜 로그인으로 회원 가입 하고자 하는 이용자의 정보 Entity
     * @see "스프링 부트와 AWS로 혼자 구현하는 웹 서비스"
     */

    public Member toEntity() {

        return Member.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(MemberRole.GUEST)
                .build();

    } // toEntity() 끝
} // class 끝
