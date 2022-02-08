package org.comunity.hongga.service;

import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberGrade;
import org.comunity.hongga.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomMemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } // CustomMemberDetailsService(MemberRepository memberRepository) 끝

    @Override @Transactional
    // 이용자가 로그인시에 DB에서 이용자 정보와 권한 정보를 가져오기 위한 Method
    // 해당 정보를 기반으로 userdetails.User 객체를 생성하여 반환
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findOneWithGradeByMemberEmail(username).map(member -> createMember(username, member)).orElseThrow(() -> new UsernameNotFoundException(username + "해당 Member를 DB에서 찾을 수 없습니다!"));
    } // loadUserByUsername(String username) 끝

    private org.springframework.security.core.userdetails.User createMember(String membername, Member member) {
        if (!member.isActivated()) {
            throw new RuntimeException(membername + "활성화 되어 있지 않습니다!");
        } // if문 끝

        // TODO MemberDetailService 처리 해야 함.
        SimpleGrantedAuthority grantedAuthorities = (Collection<? extends GrantedAuthority>)new SimpleGrantedAuthority(member.getGrade().toString());
                member.getGrade(). .stream().map(authority ->
                new SimpleGrantedAuthority(Member.builder().grade();

        return new org.springframework.security.core.userdetails.User(member.getEmail(), member.getPassword(),  grantedAuthorities);
    } // createMember(String membername, Member member) 끝

} // class 끝
