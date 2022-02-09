package org.comunity.hongga.service;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.dto.member.MemberSignUpDTO;
import org.comunity.hongga.model.entity.member.Authority;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.MemberRepository;
import org.comunity.hongga.security.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * 회원 가입 등을 위한
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
@Service public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        log.info("MemberService가 동작 하였습니다!");
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    } // 생성자 끝

    @Transactional public Member signup(MemberSignUpDTO memberSignUpDTO) {  /* 회원 가입 비즈니스 로직 */

        log.info("signup(MemberDTO memberDTO)가 동작 합니다!");
        log.info("memberDTO안에 UserName이 DB에 없으면 Authority와 Member 정보를 생성해서 MemberRepository를 통해 DB에 저장하겠습니다!");

        if (memberRepository.findOneWithGradeByMemberEmail(memberSignUpDTO.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입 되어 있는 계정 입니다!");
        } // if문 끝

        log.info("가입하는 회원의 권한 정보를 생성 하겠습니다!");
        Authority authority = Authority.builder()
                .authorityName("ROLE_GUEST")
                .build();

        log.info("권한 정보도 추가하여 회원 정보를 생성 하겠습니다!");
        Member member = Member.builder()
                .email(memberSignUpDTO.getEmail())
                .password(memberSignUpDTO.getPassword())
                .name(memberSignUpDTO.getName())
                .activated(true)
                .nickname(memberSignUpDTO.getNickname())
                .authorities(Collections.singleton(authority))
                .aboutMe(memberSignUpDTO.getAboutMe())
                .build();

        return memberRepository.save(member);

    } // signup(MemberDTO memberDTO) 끝

    // 회원 정보와 권한 정보를 가져오는 Method
    // email을 기준으로 해당 member의 member 객체와 권한 정보 가져오기
    @Transactional(readOnly = true) public Optional<Member> getServiceMemberWithGrade(String email) {

        log.info("signup(MemberDTO memberDTO)가 동작 합니다!");
        log.info("getServiceMemberWithGrade(String email)가 호출 되었습니다!");
        log.info("회원의 정보 객체와 권한 정보를 가져오는 Method 입니다!");

        return SecurityUtil.getCurrentMemberEmail().flatMap(memberRepository::findOneWithGradeByMemberEmail);

    } // getServiceMemberWithGrade() 끝

    // 회원 정보와 권한 정보를 가져오는 Method
    // SecurityContext에 저장 되어 있는 email에 해당(getCurrentMemberEmail)하는 member 정보와 권한 정보만 가져온다.
    @Transactional(readOnly = true) public Optional<Member> getServiceMemberWithGrade() {

        log.info("signup(MemberDTO memberDTO)가 동작 합니다!");
        log.info("getServiceMemberWithGrade()가 호출 되었습니다!");
        log.info("회원의 정보와 권한 정보를 가져오는 Method 입니다!");

        return SecurityUtil.getCurrentMemberEmail().flatMap(memberRepository::findOneWithGradeByMemberEmail);

    } // getServiceMemberWithGrade() 끝

} // class 끝
