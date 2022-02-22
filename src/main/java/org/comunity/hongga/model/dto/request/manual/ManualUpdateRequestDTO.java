package org.comunity.hongga.model.dto.request.manual;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Lob;

/**
 * 메뉴얼 게시판 수정 기능 관련 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.19 최초 작성
 *    주니하랑, 1.0.1, 2022.02.20 Setter 및 Builder Pattern 추가
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.20 Setter 및 Builder Pattern 추가
 * @See ""
 * @see <a href=""></a>
 */

@Builder @NoArgsConstructor @AllArgsConstructor @Data @Slf4j
public class ManualUpdateRequestDTO {

    private String title;

    // TODO - 글, 사진 (Editor 사용)
    private String content;

    private String tagContent;

} // class 끝
