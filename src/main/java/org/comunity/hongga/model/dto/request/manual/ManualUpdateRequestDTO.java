package org.comunity.hongga.model.dto.request.manual;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.entity.member.Member;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDateTime;

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

    // 수정 당시 시간으로 시각 변경 처리
    private LocalDateTime updateAt = LocalDateTime.now();

    // TODO - 글, 사진 (Editor 사용)
    private String content;

    // 사진 관련 Member 변수
    private String uuid;
    private String imgName;
    private String path;

    // TAG 관련 Member 변수
    private String tagContent;

} // class 끝
