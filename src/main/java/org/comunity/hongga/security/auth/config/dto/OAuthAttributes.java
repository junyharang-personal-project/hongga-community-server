package org.comunity.hongga.security.auth.config.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberRole;

import java.util.Map;

/**
 * 이용자 정보를 받아 Entity를 생성하는 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.03.02 최초 작성
 *    주니하랑, 1.0.1, 2022.03.03 Naver 소셜 로그인 추가 및 별명값을 받기 위한 필드 추가
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.03.03 Naver 소셜 로그인 추가 및 별명값을 받기 위한 필드 추가
 * @See "https://seokr.tistory.com/811"
 * @see <a href=""></a>
 */

@Getter @Slf4j
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String nickname;
    private String picture;

    @Builder public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String nickname, String picture) {

        log.info("OAuthAttributes의 생성자 OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture)가 호출 되었습니다!");

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
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

        log.info("OAuthAttributes의 of(String registrationId, String userNameAttributeName, Map<String, Object> attributes)가 호출 되었습니다!");
        log.info("이용자가 선택한 소셜 로그인 업체가 NAVER 인지 확인 하겠습니다!");

        if ("naver".equals(registrationId)) {

            log.info("이용자가 선택한 소셜 로그인 업체가 NAVER 입니다!");
            log.info("ofNaver(\"id\", attributes)를 호출 하겠습니다!");

            return ofNaver("id", attributes);

        } // if ("naver".equals(registrationId)) 끝

        log.info("이용자가 선택한 소셜 로그인 업체가 Google 입니다!");
        log.info("ofGoogle(userNameAttributeName, attributes)을 호출 하겠습니다!");

        return ofGoogle(userNameAttributeName, attributes);

    } // of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) 끝

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

        log.info("OAuthAttributes의 ofNaver(String id, Map<String, Object> attributes)가 호출 되었습니다!");

        log.info("application-oauth.properties에 설정한 user_name_attribute=response에 response 값을 처리 하겠습니다!");
        log.info("이것은 기준이 되는 user_name의 이름을 네이버에서는 response로 해야하고, 이유는 네이버의 회원 조회 시 반환되는 형태가 JSON이기 때문 입니다!");
        Map<String, Object> responses = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) responses.get("name"))
                .email((String) responses.get("email"))
                .nickname((String) responses.get("nickname"))
                .picture((String) responses.get("profile_image"))
                .attributes(responses)
                .nameAttributeKey(userNameAttributeName)
                .build();

    } // ofNaver(String userNameAttributeName, Map<String, Object> attributes) 끝

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        log.info("OAuthAttributes의 ofGoogle(String userNameAttributeName, Map<String, Object> attributes)이 호출 되었습니다!");

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .nickname((String) attributes.get("nickname"))
                .picture((String) attributes.get("picture"))
                .nameAttributeKey(userNameAttributeName)
                .build();

    } // ofGoogle(String userNameAttributeName, Map<String, Object> attributes) 끝


    /**
     * Member Entity 생성을 위한 Method
     * OAuthAttributes에서 Entity를 생성하는 시점은 처음 가입할 때 이며, 가입 할 때 기본 권한을 GUEST로 주기 위해 role Builder Value를 Role.GUEST로 사용
     * @return Member - 소셜 로그인으로 회원 가입 하고자 하는 이용자의 정보 Entity
     * @see "스프링 부트와 AWS로 혼자 구현하는 웹 서비스"
     */

    public Member toEntity() {

        log.info("OAuthAttributes의 toEntity()가 호출 되었습니다!");

        return Member.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .picture(picture)
                .role(MemberRole.GUEST)
                .build();

    } // toEntity() 끝
} // class 끝
