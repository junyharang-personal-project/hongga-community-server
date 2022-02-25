package org.comunity.hongga.model.dto.response.manual.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용 설명서 TAG 등록 요청 관련 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.25 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.25 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor
public class ManualTagResponseDTO {

    private Long tagNo;
    private Long manualNo;

    private String tagContent;

    public ManualTagResponseDTO (ManualTagResponseDTO manualTagResponseDTO) {

        this.tagNo = manualTagResponseDTO.tagNo;
        this.manualNo = manualTagResponseDTO.manualNo;
        this.tagContent = manualTagResponseDTO.tagContent;

    } // 생성자 끝

} // class 끝
