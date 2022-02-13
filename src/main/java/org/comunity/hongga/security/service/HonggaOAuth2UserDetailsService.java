package org.comunity.hongga.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.entity.member.OAuthMember;
import org.comunity.hongga.repository.OAuthMemberRepository;
import org.comunity.hongga.security.auth.config.dto.OAuthAttributesDTO;
import org.comunity.hongga.security.auth.config.dto.SessionUserDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * 소셜 로그인 처리를 위한 비즈니스 로직 처리용 Service
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


@Slf4j @RequiredArgsConstructor
@Service public class HonggaOAuth2UserDetailsService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuthMemberRepository oAuthMemberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스 구분 (구글, 네이버, 카카오 등 구분을 위한 내용)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /*
            Oath2 Login 진행 시 Key가 되는 필드값. Primary Key와 같은 의미로 사용
            구글의 경우 기본적으로 Code를 지원하나, 네이버, 카카오 등은 기본 지원하지 않음.
            이 후 네이버와 구글 로그인 동시 지원 시 사용
         */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        /*
            OAuth2UserService를 통해 가져온 OAth2User의 attribute를 담을 Class로 네이버 등 다른 소셜 로그인 또한 사용할 클래스
         */
        OAuthAttributesDTO attributes = OAuthAttributesDTO.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        OAuthMember member = saveOrUpdate(attributes);

        // 세션에 이용자 정보를 저장하기 위한 DTO
        httpSession.setAttribute("member", new SessionUserDTO(member));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());

    } // loadUser(OAuth2UserRequest userRequest) 끝

    private OAuthMember saveOrUpdate(OAuthAttributesDTO attributes) {  /* Google에서 이용자의 정보가 Update 되었을 때를 대비하여 update 기능 구현 */

        // 이용자 이름, 프로필 사진이 변경되면 OAuthMember Entity에도 반영
        OAuthMember oAuthMember = oAuthMemberRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(attributes.getName(), attributes.getPicture())).orElse(attributes.toEntity());

        return oAuthMemberRepository.save(oAuthMember);

    } // saveOrUpdate(OAuthAttributes attributes) 끝
} // class 끝
