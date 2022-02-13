package org.comunity.hongga.security.auth.config.jwt.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 인증 실패 처리 핸들러
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

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler { /* 필요한 권한(인가 처리)가 존재하지 않은 경우 403(Forbidden Error) 반환 */

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_FORBIDDEN);

    } // handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) 끝
} // class 끝
