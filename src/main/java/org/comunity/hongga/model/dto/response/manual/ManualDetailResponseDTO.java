package org.comunity.hongga.model.dto.response.manual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.comunity.hongga.model.entity.member.Member;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 사용 설명서 관련 상세 조회 DTO
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

@Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class ManualDetailResponseDTO {

    private Long manualNo;
    private String title;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private Member writer;
    private String content;
    private String manualTag;

} // class 끝
