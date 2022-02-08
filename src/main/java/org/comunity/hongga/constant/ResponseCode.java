package org.comunity.hongga.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 응답 코드 관련 인터페이스
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.08 최초 작성
 * @See "코드로 배우는 스프링 부트 웹 프로젝트"
 * @see <a href=""></a>
 */

@Getter @RequiredArgsConstructor
public enum ResponseCode {

    CODE_200(200, HttpStatus.OK, "작업이 완료 되었습니다!(Job complete)"),
    CODE_201(201, HttpStatus.OK,  "작업이 완료 되었습니다!(Job complete)"),
    CODE_400(400, HttpStatus.BAD_REQUEST,  "잘못된 요청 입니다!(Wrong request please check)"),
    CODE_401(401, HttpStatus.UNAUTHORIZED,  "인증 정보를 확인 해 주세요!(you are Authorized is not thing)"),
    CODE_403(403, HttpStatus.FORBIDDEN,  "이 곳에 접근할 권한이 없습니다!(There is no permission to access this service!)"),
    CODE_404(404, HttpStatus.NOT_FOUND,  "존재하지 않는 서비스 입니다!(Service that does not exist)"),
    CODE_405(405, HttpStatus.METHOD_NOT_ALLOWED,  "잘못 된 요청 방식 입니다.(Wrong request method)"),
    CODE_500(500, HttpStatus.INTERNAL_SERVER_ERROR,  "서버에 문제가 발생 하셨습니다.(server have a error)");

    private final int code;
    private final HttpStatus status;
    private final String message;

} // enum class 끝
