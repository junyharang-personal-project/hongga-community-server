package org.comunity.hongga.repository.member;

import org.comunity.hongga.model.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 회원 관련 JPA
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 *    주니하랑, 1.1.0, 2022.02.15 회원 검색 쿼리 변경
 *    주니하랑, 1.1.1, 2022.03.02 소셜 로그인 기능 구현을 위해 Method 추가
 *    주니하랑, 1.2.0, 2022.03.05 회원을 찾기 위한 Method 통합
 *    주니하랑, 1.2.1, 2022.03.06 PrincipalDetailsService 구현을 위해 Method 추가
 * </pre>
 *
 * @author 주니하랑
 * @version 주니하랑, 2022.03.06 PrincipalDetailsService 구현을 위해 Method 추가
 * @See "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.179"
 * @see <a href=""></a>
 */

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 소셜 로인으로 반환되는 값 중 email을 통해 이미 등록된 이용자 인지 여부 검사를 위한 Method
     * Local 계정으로 로그인 시도 시 로그인 요청자가 입력한 email을 통해 해당 회원이 있는지 여부 검사를 위한 용도 또한 사용
     * 회원 가입 시 중복 여부를 확인하기 위한 Method
     * @param email - 소셜 로그인 서비스에서 환하는 이용자 email 주소
     * @return Optional<Member> - Member 객체를 Null 보호 하여 반환
     * @see ""
     */

    Optional<Member> findByEmail(String email);

} // interface 끝
