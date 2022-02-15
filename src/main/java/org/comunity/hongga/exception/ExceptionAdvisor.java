package org.comunity.hongga.exception;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 유효성 검사 관련 Exception 처리 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.15 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Slf4j
@ControllerAdvice public class ExceptionAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultResponse<String>> processValidationError(MethodArgumentNotValidException exception) {

        log.info("ExceptionAdvisor가 동작 하였습니다!");
        log.info("processValidationError(MethodArgumentNotValidException exception)가 호출 되었습니다!");

        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {

            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("] 입니다!");

        } // for each 끝

        return new ResponseEntity<>(DefaultResponse.response(HttpStatus.BAD_REQUEST.value(), "유효성 검사 실패", builder.toString()), HttpStatus.BAD_REQUEST);

    } // processValidationError(MethodArgumentNotValidException exception) 끝

} // class 끝
