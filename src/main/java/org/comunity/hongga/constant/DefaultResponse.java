package org.comunity.hongga.constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 응답 Message를 위한 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.09 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.09 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Data @AllArgsConstructor @Builder
public class DefaultResponse<T> {

    // API 상태 코드
    private Integer statusCode;

    // API 부가 설명
    private String Message;

    // API 응답 데이터
    private T data;

    // 페이징 처리
    private Pagination pagination;

    // 상태 코드 + 부가 설명 반환
    public static <T> DefaultResponse<T> response(final Integer statusCode, final String Message){
        return (DefaultResponse<T>) DefaultResponse.builder()
                .statusCode(statusCode)
                .Message(Message)
                .build();
    }

    // 상태 코드 + 부가 설명 + 응답 데이터 반환
    public static <T> DefaultResponse<T> response(final Integer statusCode, final String Message, final T data){
        return (DefaultResponse<T>) DefaultResponse.builder()
                .statusCode(statusCode)
                .Message(Message)
                .data(data)
                .build();
    }

    // 상태 코드 + 부가 설명 + 응답 데이터 + 페이징 처리 반환
    public static <T> DefaultResponse<T> response(final Integer statusCode, final String Message, final T data, final Pagination pagination){
        return (DefaultResponse<T>) DefaultResponse.builder()
                .statusCode(statusCode)
                .Message(Message)
                .data(data)
                .pagination(pagination)
                .build();
    }

} // class 끝
