package org.comunity.hongga.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 응답 코드 관련 인터페이스
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 *    주니하랑, 1.0.1, 2022.02.28 응답 코드 수정
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.28 응답 코드 수정
 * @See ""
 * @see <a href=""></a>
 */

@Getter @RequiredArgsConstructor
public enum ResponseCode {

    SUCCESS(200, "성공", "Success"),
    PRESENT(200, "이미 존재하는 값 입니다!", "It is already existed!"),
    CHECK_VALUE(200, "입력값을 확인 해 주세요!", "Please check the input value!"),

    NO_CONTENT(204, "검색 결과가 없습니다.", "Not Found Data"),

    NotFoundUser(300, "사용자 정보를 찾을 수 없습니다.", "Not Found User"),
    TokenError(302, "요청한 토큰에 대한 세션정보가 없습니다. 토큰 재발급 필요.", "TokenError"),
    RequiredParam(400, "필수 파라미터 에러", "Required Parameter"),
    InvalidValueType(400, "파라미터 데이터 타입 오류", "Parameter valueType is not correct"),

    NeedSignIn(401, "로그인이 필요합니다.", "Need SignIn"),

    UnauthorisedAPICategory(403, "권한이 없는 API CategoryCategory 입니다.", "Unauthorised API Category"),
    UnauthorisedAPI(403, "권한이 없는 API 입니다.", "Unauthorised API"),
    NotAllowCSRF(403, "허용하지 않는 Referer 입니다.", "Not Allow Referer"),

    NotFoundResult(404, "결과를 찾을 수 없습니다.", "Not Found Result"),
    NotFoundAPIKey(404, "APIKey를 찾을 수 없습니다.", "Not found APIKey"),
    NotFoundAPICategory(404, "Api Category를 찾을 수 없습니다.", "Not Found Api Category"),
    NotFoundAPIConfig(404, "Api Config를 찾을 수 없습니다.", "Not Found Api Config"),
    NotFoundEvent(404, "Event를 찾을 수 없습니다.", "Not Found Event"),
    NotFoundNodeType(404, "NodeType 찾을수 없음. - %s", "Not Found NodeType. - %s"),
    EmptyIdsParameters(404, "ids 파라미터값이 없음.", "ids Parameter is Empty"),

    NotAllowMethod(405, "허용하지 않는 Method 입니다.", "Not Allow Method"),

    NotSupportedAPIType(500, "지원하지 않는 API Type 입니다.", "Not Supported API Type"),
    NotSupportedAPICategoryType(500, "지원하지 않는 API Category Type 입니다.", "Not Supported API Category Type"),
    NotAllowProtocol(500, "허용하지 않는 Protocol 입니다.", "Not Allow Protocol"),
    NotFoundFile(500, "빈 파일을 등록할 수 없습니다.", "Not Found File"),
    ServerError(500, "서버에러", "ServerError");

    Integer code;
    String messageKo;
    String messageEn;

    ResponseCode(Integer code, String messageKo, String messageEn) {
        this.code = code;
        this.messageKo = messageKo;
        this.messageEn = messageEn;
    } // 생성자 끝

//    CODE_200(200, HttpStatus.OK, "작업이 완료 되었습니다!(Job complete)"),
//    CODE_201(201, HttpStatus.OK,  "작업이 완료 되었습니다!(Job complete)"),
//    CODE_400(400, HttpStatus.BAD_REQUEST,  "잘못된 요청 입니다!(Wrong request please check)"),
//    CODE_401(401, HttpStatus.UNAUTHORIZED,  "인증 정보를 확인 해 주세요!(you are Authorized is not thing)"),
//    CODE_403(403, HttpStatus.FORBIDDEN,  "이 곳에 접근할 권한이 없습니다!(There is no permission to access this service!)"),
//    CODE_404(404, HttpStatus.NOT_FOUND,  "존재하지 않는 서비스 입니다!(Service that does not exist)"),
//    CODE_405(405, HttpStatus.METHOD_NOT_ALLOWED,  "잘못 된 요청 방식 입니다.(Wrong request method)"),
//    CODE_500(500, HttpStatus.INTERNAL_SERVER_ERROR,  "서버에 문제가 발생 하셨습니다.(server have a error)");
//
//    private final int code;
//    private final HttpStatus status;
//    private final String message;

} // enum class 끝
