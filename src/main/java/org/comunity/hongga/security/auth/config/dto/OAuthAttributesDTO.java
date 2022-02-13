package org.comunity.hongga.security.auth.config.dto;

import lombok.Builder;
import lombok.Getter;
import org.comunity.hongga.model.entity.member.OAuthMember;
import org.comunity.hongga.model.entity.member.Role;

import java.util.Map;

/**
 * OAuth2UserService를 통해 가져온 OAth2User의 attribute를 담을 Class로 네이버 등 다른 소셜 로그인 또한 사용할 클래스
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
public class OAuthAttributesDTO {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder public OAuthAttributesDTO(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;

    } // 생성자 끝

    // OAuth2User에서 반환하는 이용자 정보는 Map이기 때문에 값 하나하나를 변환하기 위한 Method
    public static OAuthAttributesDTO of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        return ofGoogle(userNameAttributeName, attributes);

    } // of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) 끝

    private static OAuthAttributesDTO ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributesDTO.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .nameAttributeKey(userNameAttributeName)
                .build();

    } // ofGoogle(String userNameAttributeName, Map<String, Object> attributes) 끝

    public OAuthMember toEntity() { /* OAuthMember Entity 생성 */

        // 가입하는 시점에 Entity를 생성한다.
        // 가입할 때의 기본 권한을 GUEST로 준다.

        return OAuthMember.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();

    } // toEntity() 끝
} // class 끝
