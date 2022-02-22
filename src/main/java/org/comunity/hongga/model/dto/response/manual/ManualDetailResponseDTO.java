package org.comunity.hongga.model.dto.response.manual;

import lombok.*;
import org.comunity.hongga.model.dto.request.manual.ManualImageDTO;
import org.comunity.hongga.model.dto.request.manual.ManualTagDTO;
import org.comunity.hongga.model.entity.manual.ManualTag;
import org.comunity.hongga.model.entity.member.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ManualDetailResponseDTO {

    private Long manualNo;
    private String title;
    private String writer;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String content;

    private List<ManualImageDTO> imageDTOList = new ArrayList<>();
    private List<ManualTagDTO> manualTagDTOList = new ArrayList<>();
} // class 끝
