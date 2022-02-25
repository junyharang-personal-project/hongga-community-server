package org.comunity.hongga.model.dto.response.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.base.BaseDateTime;
import org.comunity.hongga.model.entity.member.Member;

import java.time.LocalDateTime;

/**
 * 사용 설명서 목록 조회 관련 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.23 최초 작성
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.23 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ManualListSearchResponseDTO {

    private Long manualNo;

    private String title;

    private String nickname;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    //TODO - 좋아요 수

} // class 끝
