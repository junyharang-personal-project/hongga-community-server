package org.comunity.hongga.constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 응답 Message를 위한 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.09 최초 작성
 *    주니하랑, 1.0.1, 2022.03.02 ResponseCode 상세 구현을 위해 수정
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.03.02 ResponseCode 상세 구현을 위해 수정
 * @See ""
 * @see <a href=""></a>
 */

@Data @AllArgsConstructor @Builder
public class DefaultResponse<T> {

    // API 상태 코드
    private Integer statusCode;

    // API 부가 설명 (한글)
    private String MessageKo;

    // API 부가 설명 (영어)
    private String MessageEn;

    // API 응답 데이터
    private T data;

    // 페이징 처리
    private Pagination pagination;

    // 상태 코드 + 부가 설명 반환
    public static <T> DefaultResponse<T> response(final Integer statusCode, final String MessageKo, final String MessageEn){
        return (DefaultResponse<T>) DefaultResponse.builder()
                .statusCode(statusCode)
                .MessageKo(MessageKo)
                .MessageEn(MessageEn)
                .build();
    }

    // 상태 코드 + 부가 설명 + 응답 데이터 반환
    public static <T> DefaultResponse<T> response(final Integer statusCode, final String MessageKo, final String MessageEn, final T data){
        return (DefaultResponse<T>) DefaultResponse.builder()
                .statusCode(statusCode)
                .MessageKo(MessageKo)
                .MessageEn(MessageEn)
                .data(data)
                .build();
    }

    // 상태 코드 + 부가 설명 + 응답 데이터 + 페이징 처리 반환
    public static <T> DefaultResponse<T> response(final Integer statusCode, final String MessageKo, final String MessageEn, final T data, final Pagination pagination){
        return (DefaultResponse<T>) DefaultResponse.builder()
                .statusCode(statusCode)
                .MessageKo(MessageKo)
                .MessageEn(MessageEn)
                .data(data)
                .pagination(pagination)
                .build();
    }

} // class 끝
