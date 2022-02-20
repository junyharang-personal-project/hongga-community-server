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

    private String tagContent0;
    private String tagContent1;
    private String tagContent2;
    private String tagContent3;
    private String tagContent4;
    private String tagContent5;
    private String tagContent6;
    private String tagContent7;
    private String tagContent8;
    private String tagContent9;

} // class 끝
