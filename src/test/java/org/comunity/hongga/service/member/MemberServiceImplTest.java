package org.comunity.hongga.service.member;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.member.MemberSignInRequestDTO;
import org.comunity.hongga.model.dto.response.member.MemberSignInResponseDTO;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberGrade;
import org.comunity.hongga.repository.member.MemberRepository;
import org.comunity.hongga.security.util.JwtUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * 회원 관련 Service Test Code
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.0.1, 2022.02.15 로그인 관련 Test Code 구현
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.15 로그인 관련 Test Code 구현
 * @See ""
 * @see <a href=""></a>
 */

@RunWith(SpringRunner.class) @Slf4j

public class MemberServiceImplTest {

    MemberServiceImpl memberServiceImpl;
    MockHttpServletRequest httpServletRequest;
    MockHttpSession mockHttpSession;

    @Mock MemberRepository memberRepository;
    @Mock Execution execution;
    @Mock JwtUtil jwtUtil;

//    @Before public void setting() {
//
//        MockitoAnnotations.openMocks(this);
//        mockHttpSession = new MockHttpSession();
//        httpServletRequest = new MockHttpServletRequest();
//        jwtUtil = new JwtUtil("7KeA6riI7J2Aand07YWM7Iqk7Yq47KSR7J6F64uI64uk7J206rGw7LC465Oc65+96rKM7Ja066C16rOg7Z6Y65Oc64Sk7JqU67O17J6h7ZWc6rKDCg==");
//        memberService = new MemberService(memberRepository, pwd);

//    } // setting() 끝

    @After public void clean() { mockHttpSession.clearAttributes(); } // clean() 끝

//    @Test public void 회원가입() {
//
//        // given
//        String email = "test@hongga.com";
//        String password = "hong123456";
//        String name = "홍주니";
//        String nickName = "주니하랑";
//        String phoneNumber = "010-3939-4848";
//        String picture = "sdoijgoij.jpg";
//        String aboutMe = "안녕하세요! 우리 가족에게 언제나 좋은 일만 가득하길 바랍니다!";
//        boolean activated = true;
//
//        MemberSignUpRequestDTO memberSignUpRequestDTO = new MemberSignUpRequestDTO(email, password, name, nickName, phoneNumber, picture, aboutMe, activated);
//
//        Member mockMember = Member.builder()
//                .email(memberSignUpRequestDTO.getEmail())
//                .password(memberSignUpRequestDTO.getPassword())
//                .name(memberSignUpRequestDTO.getName())
//                .nickname(memberSignUpRequestDTO.getNickname())
//                .phoneNumber(memberSignUpRequestDTO.getPhoneNumber())
//                .picture(memberSignUpRequestDTO.getPicture())
//                .aboutMe(memberSignUpRequestDTO.getAboutMe())
//                .grade(MemberGrade.ROLE_GUEST)
//                .activated(memberSignUpRequestDTO.isActivated())
//                .build();
//
//        // when
//        given(memberRepository.save(any())).willReturn(mockMember);
//
//        DefaultResponse result = memberService.signUp(memberSignUpRequestDTO);
//
//        // then
//        assertThat(result.getMessage()).isEqualTo("회원가입 성공 하였습니다!");
//    } // 회원가입() 끝

//    @Test public void 로그인() {
//
//        // given
//        String email = "test@hongga.com";
//        String password = "hong123456";
//        String name = "홍주니";
//        String nickName = "주니하랑";
//        String phoneNumber = "010-3939-4848";
//        String picture = "sdoijgoij.jpg";
//        String aboutMe = "안녕하세요! 우리 가족에게 언제나 좋은 일만 가득하길 바랍니다!";
//        boolean activated = true;
//
//        Member mockMember = Member.builder()
//                .email(email)
//                .password(password)
//                .name(name)
//                .nickname(nickName)
//                .phoneNumber(phoneNumber)
//                .picture(picture)
//                .aboutMe(aboutMe)
//                .grade(MemberGrade.ROLE_GUEST)
//                .activated(activated)
//                .build();
//
//        MemberSignInRequestDTO memberSignInRequestDTO = new MemberSignInRequestDTO(email, password);
//
//        // when
//        given(memberRepository.findByEmail(email)).willReturn(Optional.of(email));
//
//        given(memberRepository.findByMember(email, password)).willReturn(Optional.of(mockMember));
//
//        DefaultResponse<MemberSignInResponseDTO> response = memberServiceImpl.signIn(memberSignInRequestDTO);
//
//        // then
//        assertThat(response.getMessage()).isEqualTo("로그인에 성공 하였습니다!");
//
//    } // 로그인() 끝

} // class 끝