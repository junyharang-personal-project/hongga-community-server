package org.comunity.hongga.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ResponseCode;
import org.comunity.hongga.model.dto.request.member.MemberSignInRequestDTO;
import org.comunity.hongga.model.dto.request.member.MemberSignUpRequestDTO;
import org.comunity.hongga.model.dto.response.member.MemberSignInResponseDTO;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.member.MemberRepository;
import org.comunity.hongga.security.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 회원 가입 관련 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.0.1, 2022.02.15 로그인 관련 비즈니스 로직 구현
 *    주니하랑, 1.0.2, 2022.03.02 응답 코드 구체화로 인한 return문 수정
 *    주니하랑, 1.0.3, 2022.03.02 Service와 Impl 분리로 인한 Overriding
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.3, 2022.03.02 Service와 Impl 분리로 인한 Overriding
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Service public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원 가입 서비스
     * @param memberSignUpRequestDTO - 회원 가입을 위한 이용자 입력값을 담은 DTO
     * @return DefaultResponse - 서버 처리 여부에 해당하는 Status Code 반환을 위한 객체
     * @see ""
     */

    @Override
    public DefaultResponse signUp(MemberSignUpRequestDTO memberSignUpRequestDTO) {

        log.info("MemberService가 동작하였습니다!");
        log.info("signUp(MemberSignUpRequestDTO memberSignUpRequestDTO)가 호출 되었습니다!");

        log.info("회원 가입을 요청한 이용자의 Email 주소가 중복 되었는지 확인 하겠습니다!");
        String requestEmail = memberSignUpRequestDTO.getEmail();

        // TODO - Password 암호화 처리 필요
        log.info("이용자의 패스워드를 암호화 하겠습니다!");
        memberSignUpRequestDTO.setPassword(passwordEncoder.encode(memberSignUpRequestDTO.getPassword())); ;

        Optional<String> checkEmail = memberRepository.findBytoMemberEmail(requestEmail);

        return checkEmail.map(email -> DefaultResponse.response(ResponseCode.PRESENT.getCode(), ResponseCode.PRESENT.getMessageKo(), ResponseCode.PRESENT.getMessageEn()))
                .orElseGet(() -> {

                    log.info("중복된 Email이 없으므로, DB에 저장 하겠습니다!");
                    memberRepository.save(memberSignUpRequestDTO.toEntity());

                    return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn());
                });
    } // signUp(MemberSignUpRequestDTO memberSignUpRequestDTO) 끝


    /**
     * 로그인 서비스
     * @param memberSignInRequestDTO - 로그인을 위한 이용자 입력값을 담은 DTO
     * @return DefaultResponse<MemberSignInResponseDTO> - JWT(Access, Refresh)와 회원 고유 번호, 회원 등급, 회원 별명을 담은 DTO를 HTTP Status Code와 함께 로그인 처리를 위해 반환
     * @see ""
     */

    @Override
    @Transactional public DefaultResponse<MemberSignInResponseDTO> signIn(MemberSignInRequestDTO memberSignInRequestDTO) {

        Optional<String> loginEmail = memberRepository.findBytoMemberEmail(memberSignInRequestDTO.getEmail());

        return loginEmail.map(email -> {

            Optional<Member> loginMember = memberRepository.findByMember(email, memberSignInRequestDTO.getPassword());

            return loginMember.map(member -> {

                if (!passwordEncoder.matches(memberSignInRequestDTO.getPassword(), loginMember.get().getPassword())) {

                    DefaultResponse.response(ResponseCode.CHECK_VALUE.getCode(), ResponseCode.CHECK_VALUE.getMessageKo(), ResponseCode.CHECK_VALUE.getMessageEn());

                } // if (!passwordEncoder.matches(memberSignInRequestDTO.getPassword(), loginMember.get().getPassword())) 끝

                String accessToken = JwtUtil.createAccessToken(member.getMemberNo(), member.getRole());

                String refreshToken = JwtUtil.createRefreshToken(member.getMemberNo(), member.getRole());

                member.setRefreshToken(refreshToken);

                return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), new MemberSignInResponseDTO(accessToken, refreshToken, member.getMemberNo(), member.getRole(), member.getNickname()));

            }).orElseGet(() -> DefaultResponse.response(ResponseCode.CHECK_VALUE.getCode(), ResponseCode.CHECK_VALUE.getMessageKo(), ResponseCode.SUCCESS.getMessageEn()));

        }).orElseGet(() -> DefaultResponse.response(ResponseCode.CHECK_VALUE.getCode(), ResponseCode.CHECK_VALUE.getMessageKo(), ResponseCode.SUCCESS.getMessageEn()));

    } // signIn(MemberSignInRequestDTO memberSignInRequestDTO) 끝
} // class 끝
