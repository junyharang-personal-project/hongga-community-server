package org.comunity.hongga.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.member.MemberRepository;
import org.comunity.hongga.security.auth.config.dto.OAuthAttributes;
import org.comunity.hongga.security.auth.config.dto.SessionUser;
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
 * 소셜 로그인을 통해 각 소셜 서비스에서 가져온 이용자 정보 등을 기반으로 가입, 정보 수정, 세션 저장등 수행
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.03.02 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.03.02 최초 작성
 * @See "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.181"
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Service public class CustomOAth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("OAuth2UserService<OAuth2UserRequest, OAuth2User>를 구현한 구현체 CustomOAth2UserService의 loadUser(OAuth2UserRequest userRequest)가 호출 되었습니다!");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        log.info("현재 로그인 진행 중인 소셜 로그인 서비스를 구분하는 Code를 생성 하겠습니다! (네이버, 구글, 등등을 구분하기 위해 Code를 생성 합니다!");
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        log.info("OAuth2 로그인 진행 시 Key가 되는 필드값 즉, PK와 같은 값을 만들겠습니다!");
        // 구글의 경우 기본 코드 지원하나, 네이버, 카카오는 기본 지원하지 않는다. 구글의 기본 코드는 "sub"이며, 다수의 소셜 로그인 이용 시 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        log.info("OAuth2UserService를 통해 가져온 OAuth2user의 attribute를 OAuthAttributes Class에 담겠습니다!");
        // 네이버 등 다른 소셜 로그인도 이 Class 사용
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        log.info("saveOrUpdate(attributes)를 호출하겠습니다!");
        Member member = saveOrUpdate(attributes);

        log.info("Session 이용자 정보를 저장하기 위한 DTO SessionUser 객체를 만들겠습니다!");
        httpSession.setAttribute("member", new SessionUser(member));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    } // loadUser(OAuth2UserRequest userRequest) 끝

    private Member saveOrUpdate(OAuthAttributes attributes) {

        log.info("OAuth2UserService<OAuth2UserRequest, OAuth2User>를 구현한 구현체 CustomOAth2UserService의 saveOrUpdate(OAuthAttributes attributes)가 호출 되었습니다!");
        log.info("매개 변수로 전달 된 객체에서 이용자의 Email주소를 꺼내 DB에 회원 정보가 있는지 찾고, 해당 회원 정보가 있지만, 이름과 사진이 변경 되었으면 수정을 하고, 아니면 회원 가입을 시키기 위해 DB 저장을 하겠습니다!");

        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return memberRepository.save(member);

    } // saveOrUpdate(OAuthAttributes attributes) 끝
} // class 끝
