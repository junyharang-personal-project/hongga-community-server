package org.comunity.hongga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 소셜 로그인용 회원 관련 JPA
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.13 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.13 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

public interface OAuthMemberRepository extends JpaRepository<OAuthMember, Long> {

    // 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 이용자인지 처음 가입한 이용자인지를 판단하기 위한 Method
    Optional<OAuthMember> findByEmail(String email);

} // interface 끝
