package org.comunity.hongga.model.dto.response.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용 설명서 검색 기능 중 내용 검색 관련 응답 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.28 최초 작성
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.28 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ManualListContentSearchResponseDTO {

    private Long manualNo;

    private String title;

    private String nickname;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private String content;

    //TODO - 좋아요 수

} // class 끝
