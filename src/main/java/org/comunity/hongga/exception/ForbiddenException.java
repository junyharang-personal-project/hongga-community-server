package org.comunity.hongga.exception;

import org.comunity.hongga.constant.ResponseCode;
import org.springframework.http.HttpStatus;

/**
 * Exception 처리 403 Error
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

public class ForbiddenException extends RuntimeException{

    protected final HttpStatus httpStatus;
    protected final String message;

    {   httpStatus = ResponseCode.CODE_403.getStatus();
        message = ResponseCode.CODE_403.getMessage();   }

    public ForbiddenException() {
        super();
    } // ForbiddenExcption() 끝
    public ForbiddenException(String message) {
        super(message);
    } // ForbiddenExcption() 끝
    public ForbiddenException(String message, Throwable throwable) { super(message, throwable); } // ForbiddenExcption(String message, Throwable throwable) 끝
} // class 끝
