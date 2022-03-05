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
 *    주니하랑, 1.1.0, 2022.03.05 암호화된 비밀번호 비교로 로그인 로직 구현 완료
 *    주니하랑, 1.1.1, 2022.03.05 회원 가입 코드 수정
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.1, 2022.03.05 회원 가입 코드 수정
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Service public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원 가입 서비스
     *
     * @param memberSignUpRequestDTO - 회원 가입을 위한 이용자 입력값을 담은 DTO
     * @return DefaultResponse - 서버 처리 여부에 해당하는 Status Code 반환을 위한 객체
     * @see ""
     */

    @Override
    public DefaultResponse signUp(MemberSignUpRequestDTO memberSignUpRequestDTO) {

        log.info("MemberService의 signUp(MemberSignUpRequestDTO memberSignUpRequestDTO)가 호출 되었습니다!");

        String requestEmail = memberSignUpRequestDTO.getEmail();

        log.info("DB에서 로그인 요청 이용자가 입력한 Email 주소를 통해 해당 회원이 등록 되어 있는지 찾겠습니다!");
//        Optional<String> checkEmail = memberRepository.findBytoMemberEmail(requestEmail);

        Optional<Member> checkEmail = memberRepository.findByEmail(requestEmail);

        if (checkEmail.isEmpty()) {

            log.info("회원 가입 요청 이용자가 입력한 Email 주소가 DB에 저장 되어 있지 않습니다!");
            log.info("회원 가입 입력란 중 중복 불가한 값에 대해 중복 값이 있는지 확인 하겠습니다!");

            return checkEmail.filter(member -> member.getNickname().equals(memberSignUpRequestDTO.getEmail()))
                .map(member -> {
                    log.info("이미 존재하는 별명 입니다! 200 Code와 함께 \"이미 존재하는 값 입니다!\" 반환 하겠습니다!");

                    return DefaultResponse.response(ResponseCode.PRESENT.getCode(), ResponseCode.PRESENT.getMessageKo(), ResponseCode.PRESENT.getMessageEn());

                }).orElse(((checkEmail.filter(member -> member.getPhoneNumber().equals(memberSignUpRequestDTO.getPhoneNumber())))
                .map(member -> {

                    log.info("이미 존재하는 핸드폰 번호 입니다! 200 Code와 함께 \"이미 존재하는 값 입니다!\" 반환 하겠습니다!");

                    return DefaultResponse.response(ResponseCode.PRESENT.getCode(), ResponseCode.PRESENT.getMessageKo(), ResponseCode.PRESENT.getMessageEn());

                })).orElseGet(() -> {

                    log.info("중복된 내용이 없으므로, DB에 저장 하겠습니다!");

                    log.info("이용자의 패스워드를 암호화 하겠습니다!");
                    memberSignUpRequestDTO.setPassword(passwordEncoder.encode(memberSignUpRequestDTO.getPassword()));

                    memberRepository.save(memberSignUpRequestDTO.toEntity());

                    return DefaultResponse.response(ResponseCode.CREATE.getCode(), ResponseCode.CREATE.getMessageKo()+"회원 가입 완료!", ResponseCode.CREATE.getMessageEn());
                }));

        } else {

            log.info("이미 존재하는 Email 주소 입니다! 200 Code와 함께 \"이미 존재하는 값 입니다!\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.PRESENT.getCode(), ResponseCode.PRESENT.getMessageKo(), ResponseCode.PRESENT.getMessageEn());

        } // if-else (checkEmail.isEmpty()) 끝

    } // signUp(MemberSignUpRequestDTO memberSignUpRequestDTO) 끝


    /**
     * 로그인 서비스
     *
     * @param memberSignInRequestDTO - 로그인을 위한 이용자 입력값을 담은 DTO
     * @return DefaultResponse<MemberSignInResponseDTO> - JWT(Access, Refresh)와 회원 고유 번호, 회원 등급, 회원 별명을 담은 DTO를 HTTP Status Code와 함께 로그인 처리를 위해 반환
     * @see ""
     */

    @Override
    @Transactional
    public DefaultResponse<MemberSignInResponseDTO> signIn(MemberSignInRequestDTO memberSignInRequestDTO) {

        log.info("MemberService의 signIn(MemberSignInRequestDTO memberSignInRequestDTO)가 동작하였습니다!");
        log.info("로그인 요청 이용자 Email 값 : " + memberSignInRequestDTO.getEmail() + ", 패스워드 값 : " + memberSignInRequestDTO.getPassword());
        log.info("DB에서 이용자가 입력한 email 주소를 통해 존재하는 회원이 있는지 찾아 보겠습니다!");

        Optional<Member> loginEmail = memberRepository.findByEmail(memberSignInRequestDTO.getEmail());

        log.info("DB에서 Email 주소를 통해 조회된 회원의 Email 주소와 이용자가 입력한 패스워드를 통해 DB에서 해당 회원이 존재하는지 확인 하겠습니다!");

        if (loginEmail.isPresent()) {

            log.info("로그인 요청 이용자이 입력한 Email 주소가 DB 값과 일치 합니다!");

            if (passwordEncoder.matches(memberSignInRequestDTO.getPassword(), loginEmail.get().getPassword())) {

                log.info("로그인 요청 이용자이 입력한 패스워드가가 DB 값과 일치 합니다!");

                log.info("로그인 요청 이용자 정보가 존재합니다! 해당 이용자의 고유번호와 회원 역할을 통해 Access Token과 Refresh Token을 생성하겠습니다!");

                return loginEmail.map(member -> {

                    String accessToken = JwtUtil.createAccessToken(loginEmail.get().getMemberNo(), loginEmail.get().getRole());

                    String refreshToken = JwtUtil.createRefreshToken(loginEmail.get().getMemberNo(), loginEmail.get().getRole());

                    log.info("JWT가 모두 생성 되었으며, Refresh Token은 회원 Table에 저장 해 두겠습니다!");

                    member.setRefreshToken(refreshToken);

                    log.info("Login이 성공 하였습니다! 200 Code와 함께 \"성공\" 과 \"Sucess\"와 로그인 요청 이용자의 고유 번호, 회원 역할, Access Token, RefreshToken 반환 하겠습니다!");

                    return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), new MemberSignInResponseDTO(accessToken, refreshToken, loginEmail.get().getMemberNo(), loginEmail.get().getRole(), loginEmail.get().getNickname()));

                }).orElseGet(() -> DefaultResponse.response(ResponseCode.CHECK_VALUE.getCode(), "계정을 확인 해 주세요!", "Check your Identity Value"));

            } // if (passwordEncoder.matches(memberSignInRequestDTO.getPassword(), loginEmail.getPassword())) 끝

            log.info("Login이 실패 하였습니다! 200 Code와 함께 \"Password가 일치 하지 않습니다!\" 와 \"Sucess\"를 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.CHECK_VALUE.getCode(), "Password가 일치하지 않습니다!", ResponseCode.SUCCESS.getMessageEn());

        } // if (memberSignInRequestDTO.getEmail().equals(loginEmail.getEmail())) 끝

        log.info("Login이 실패 하였습니다! 200 Code와 함께 \"ID가 일치 하지 않습니다!\" 와 \"Sucess\"를 반환 하겠습니다!");

        return DefaultResponse.response(ResponseCode.CHECK_VALUE.getCode(), "ID가 일치 하지 않습니다!", ResponseCode.SUCCESS.getMessageEn());

    } // signIn(MemberSignInRequestDTO memberSignInRequestDTO) 끝

} // class 끝
