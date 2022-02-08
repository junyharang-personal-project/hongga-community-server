package org.comunity.hongga.security.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * 인증 처리 관련 설정 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.0, 2022.02.08 최초 작성
 * @See ""
 * @see <a href=""></a>
 */


@NoArgsConstructor @Slf4j
public class SecurityUtil {

    public static Optional<String> getCurrentMemberEmail() { /* Security Context의 Authentication 객체를 이용 member 이름 추출 */

        log.debug("SecurityUtil Class가 호출 되었습니다!");
        log.debug("getCurrentMemberName()이 동작 하였습니다!");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {   // Security Context의 Authentication 객체가 비어있다면?

            log.debug("Security Context에 인증 정보가 존재 하지 않습니다!");

            return Optional.empty();
        } // if문 끝

        String memberName = null;

        if (authentication.getPrincipal() instanceof UserDetails) {

            UserDetails springSecurityMember = (UserDetails) authentication.getPrincipal();

            memberName = springSecurityMember.getUsername();

        } else if (authentication.getPrincipal() instanceof String) {

            memberName = (String) authentication.getPrincipal();

        } // if-else if 끝

        return Optional.ofNullable(memberName);

    } // getCurrentMemberName() 끝

} // class 끝
