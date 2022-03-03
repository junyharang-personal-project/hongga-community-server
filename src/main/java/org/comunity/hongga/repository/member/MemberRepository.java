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
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.1, 2022.03.02 소셜 로그인 기능 구현을 위해 Method 추가
 * @See "스프링 부트와 AWS로 혼자 구현하는 웹 서비스 P.179"
 * @see <a href=""></a>
 */

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select m.email from Member m where m.email =:email")
    Optional<String> findBytoMemberEmail(@Param("email") String email);

    @Query(value = "select m.email, m.password from Member m where m.email =:email")
    Optional<Member> findByEmailImsi(@Param("email") String email);

    @Query("select m from Member m where m.email =:email and m.password =:password")
    Optional<Member> findByMember(@Param("email") String email, @Param("password") String password);


    /**
     * 소셜 로그인으로 반환되는 값 중 email을 통해 이미 등록된 이용자 인지 여부 검사를 위한 Method
     * @param email - 소셜 로그인 서비스에서 환하는 이용자 email 주소
     * @return Optional<Member> - Member 객체를 Null 보호하여 반환
     * @see ""
     */

    Optional<Member> findByEmail(String email);

} // interface 끝
