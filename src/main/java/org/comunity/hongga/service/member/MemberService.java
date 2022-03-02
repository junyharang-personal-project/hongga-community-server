package org.comunity.hongga.service.member;

import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.member.MemberSignInRequestDTO;
import org.comunity.hongga.model.dto.request.member.MemberSignUpRequestDTO;
import org.comunity.hongga.model.dto.response.member.MemberSignInResponseDTO;

/**
 * 회원 가입 관련 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.03.02 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.03.02 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

public interface MemberService {


    /**
     * 회원 가입 서비스
     * @param memberSignUpRequestDTO - 회원 가입을 위한 이용자 입력값을 담은 DTO
     * @return DefaultResponse - 서버 처리 여부에 해당하는 Status Code 반환을 위한 객체
     * @see ""
     */

    DefaultResponse signUp(MemberSignUpRequestDTO memberSignUpRequestDTO);


    /**
     * 로그인 서비스
     * @param memberSignInRequestDTO - 로그인을 위한 이용자 입력값을 담은 DTO
     * @return DefaultResponse<MemberSignInResponseDTO> - JWT(Access, Refresh)와 회원 고유 번호, 회원 등급, 회원 별명을 담은 DTO를 HTTP Status Code와 함께 로그인 처리를 위해 반환
     * @see ""
     */

    DefaultResponse<MemberSignInResponseDTO> signIn(MemberSignInRequestDTO memberSignInRequestDTO);

} // interface 끝
