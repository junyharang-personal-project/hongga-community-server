package org.comunity.hongga.repository;

import org.comunity.hongga.model.entity.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 회원 관련 JPA
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

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = "authorities") // Query 수행 시 Lazy 조회가 아니고, Eager 조회로 Authorities 정보를 같이 가져오게 하기 위한 설정
        // Member의 이름을 가져오는데, 권한 정보를 같이 가져오게 하기 위함.
    Optional<Member> findOneWithAuthoritiesByEmail(String email);

    @Query("select m.email from Member m where m.email =:email")
    Member findByEmail(@Param("email") String email);

} // interface 끝
