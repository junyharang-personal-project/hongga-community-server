package org.comunity.hongga.model.dto.response.manual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.comunity.hongga.model.entity.member.Member;

import java.time.LocalDateTime;

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
