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
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.0, 2022.02.15 회원 검색 쿼리 변경
 * @See ""
 * @see <a href=""></a>
 */

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select m.email, m.password from Member m where m.email =:email")
    Optional<String> findByEmail(@Param("email") String email);

    @Query(value = "select m.email, m.password from Member m where m.email =:email")
    Optional<Member> findByEmailImsi(@Param("email") String email);

    @Query("select m from Member m where m.email =:email and m.password =:password")
    Optional<Member> findByMember(@Param("email") String email, @Param("password") String password);

} // interface 끝
