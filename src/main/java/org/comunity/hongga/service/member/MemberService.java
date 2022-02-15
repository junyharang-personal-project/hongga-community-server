package org.comunity.hongga.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.member.MemberSignInRequestDTO;
import org.comunity.hongga.model.dto.request.member.MemberSignUpRequestDTO;
import org.comunity.hongga.model.dto.response.member.MemberSignInResponseDTO;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.member.MemberRepository;
import org.comunity.hongga.security.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 회원 가입 관련 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.15 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Service public class MemberService {

    private final MemberRepository memberRepository;

    public DefaultResponse signUp(MemberSignUpRequestDTO memberSignUpRequestDTO) {

        log.info("MemberService가 동작하였습니다!");
        log.info("signUp(MemberSignUpRequestDTO memberSignUpRequestDTO)가 호출 되었습니다!");

        log.info("회원 가입을 요청한 이용자의 Email 주소가 중복 되었는지 확인 하겠습니다!");
        String requestEmail = memberSignUpRequestDTO.getEmail();

        Optional<String> checkEmail = memberRepository.findByEmail(requestEmail);

        return checkEmail.map(email -> DefaultResponse.response(HttpStatus.OK.value(), "이미 존재하는 Email 입니다!"))
                .orElseGet(() -> {

                    log.info("중복된 Email이 없으므로, DB에 저장 하겠습니다!");
                    memberRepository.save(memberSignUpRequestDTO.toEntity());

                    return DefaultResponse.response(HttpStatus.OK.value(), "회원가입 성공 하였습니다!");
                });
    } // signUp(MemberSignUpRequestDTO memberSignUpRequestDTO) 끝

    @Transactional public DefaultResponse<MemberSignInResponseDTO> signIn(MemberSignInRequestDTO memberSignInRequestDTO) {

        Optional<String> loginEmail = memberRepository.findByEmail(memberSignInRequestDTO.getEmail());

        return loginEmail.map(email -> {

            Optional<Member> loginMember = memberRepository.findByMember(email, memberSignInRequestDTO.getPassword());

            return loginMember.map(member -> {

                String accessToken = JwtUtil.createAccessToken(member.getMemberNo(), member.getGrade());

                String refreshToken = JwtUtil.createRefreshToken(member.getMemberNo(), member.getGrade());

                member.setRefreshToken(refreshToken);

                return DefaultResponse.response(HttpStatus.OK.value(), "로그인에 성공 하였습니다!", new MemberSignInResponseDTO(accessToken, refreshToken, member.getMemberNo(), member.getGrade(), member.getNickname()));

            }).orElseGet(() -> DefaultResponse.response(HttpStatus.OK.value(), "비밀번호를 확인 해 주세요!"));

        }).orElseGet(() -> DefaultResponse.response(HttpStatus.OK.value(), "아이디를 확인 해 주세요!"));

    } // signIn(MemberSignInRequestDTO memberSignInRequestDTO) 끝
} // class 끝
