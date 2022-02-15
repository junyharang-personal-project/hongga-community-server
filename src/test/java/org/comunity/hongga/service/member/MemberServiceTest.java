package org.comunity.hongga.service.member;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.member.MemberSignUpRequestDTO;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberGrade;
import org.comunity.hongga.repository.member.MemberRepository;
import org.comunity.hongga.security.util.JwtUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) @Slf4j

public class MemberServiceTest {

    MemberService memberService;
    MockHttpServletRequest httpServletRequest;
    MockHttpSession mockHttpSession;

    @Mock MemberRepository memberRepository;
    @Mock Execution execution;
    @Mock JwtUtil jwtUtil;

    @Before public void setting() {

        MockitoAnnotations.openMocks(this);
        mockHttpSession = new MockHttpSession();
        httpServletRequest = new MockHttpServletRequest();
        jwtUtil = new JwtUtil("7KeA6riI7J2Aand07YWM7Iqk7Yq47KSR7J6F64uI64uk7J206rGw7LC465Oc65+96rKM7Ja066C16rOg7Z6Y65Oc64Sk7JqU67O17J6h7ZWc6rKDCg==");
        memberService = new MemberService(memberRepository);

    } // setting() 끝

    @After public void clean() { mockHttpSession.clearAttributes(); } // clean() 끝

    @Test public void 회원가입() {

        // given
        String email = "test@hongga.com";
        String password = "hong123456";
        String name = "홍주니";
        String nickName = "주니하랑";
        String phoneNumber = "010-3939-4848";
        String picture = "sdoijgoij.jpg";
        String aboutMe = "안녕하세요! 우리 가족에게 언제나 좋은 일만 가득하길 바랍니다!";

        MemberSignUpRequestDTO memberSignUpRequestDTO = new MemberSignUpRequestDTO(email, password, name, nickName, phoneNumber, picture, aboutMe);

        Member mockMember = Member.builder()
                .email(memberSignUpRequestDTO.getEmail())
                .password(memberSignUpRequestDTO.getPassword())
                .name(memberSignUpRequestDTO.getName())
                .nickname(memberSignUpRequestDTO.getNickname())
                .phoneNumber(memberSignUpRequestDTO.getPhoneNumber())
                .picture(memberSignUpRequestDTO.getPicture())
                .aboutMe(memberSignUpRequestDTO.getAboutMe())
                .grade(MemberGrade.GUEST)
                .build();

        // when
        given(memberRepository.save(any())).willReturn(mockMember);

        DefaultResponse result = memberService.signUp(memberSignUpRequestDTO);

        // then
        assertThat(result.getMessage()).isEqualTo("회원가입 성공 하였습니다!");
    }

} // class 끝