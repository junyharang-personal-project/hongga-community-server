package org.comunity.hongga.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 회원 생성 등을 위한 Business Logic
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

@Slf4j
@Component("memberDetailService") public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } // MemberDetailService(MemberRepository memberRepository) 끝

    @Override @Transactional
    // 이용자가 로그인시에 DB에서 이용자 정보와 권한 정보를 가져오기 위한 Method
    // 해당 정보를 기반으로 userdetails.User 객체를 생성하여 반환
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("MemberDetailService가 동작하였습니다!");
        log.info("loadUserByUsername(String username)가 호출 되었습니다! 이용자가 로그인을 시도 합니다!");

        return memberRepository.findOneWithGradeByMemberEmail(username).map(member -> createMember(username, member)).orElseThrow(() ->
                new UsernameNotFoundException("해당 회원을 찾을 수 없습니다!"));

    } // loadUserByUsername(String username) 끝

    private org.springframework.security.core.userdetails.User createMember(String username, Member member) {

        log.info("MemberDetailService가 동작하였습니다!");
        log.info("createMember(String username, Member member)가 호출 되었습니다!");

        if (!member.isActivated()) {
            throw new RuntimeException(username + "해당 계정이 비활성화 상태 입니다!");
        } // if (!member.isActivated()) 끝

        List<SimpleGrantedAuthority> grantedAuthorities = member.getAuthorities().stream().map(authority ->
                new SimpleGrantedAuthority(authority.getAuthorityName())).collect(Collectors.toList());

        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);

    } // createMember(String username, Member member) 끝

} // class 끝
