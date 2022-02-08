package org.comunity.hongga.exception;

import org.comunity.hongga.constant.ResponseCode;
import org.springframework.http.HttpStatus;

/**
 * Exception 처리 404 Error
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

public class NotFoundException extends RuntimeException{

    protected final HttpStatus httpStatus;
    protected final String message;

    { httpStatus = ResponseCode.CODE_404.getStatus();
        message = ResponseCode.CODE_404.getMessage(); }

    public NotFoundException() { super(); } // NotFoundException() 끝
    public NotFoundException(String message) { super(message); } // NotFoundException(String message) 끝
    public NotFoundException(String message, Throwable throwable) { super(message, throwable); } // NotFoundException(String message, Throwable throwable) 끝

} // class 끝
