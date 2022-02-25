package org.comunity.hongga.model.dto.request.manual.tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.ManualTag;

import java.util.Optional;

/**
 * 메뉴얼 Tag 관련 DTO
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

@Getter @NoArgsConstructor
public class ManualTagInsertRequestDTO {

    private String tagContent;

    public ManualTag toEntity(Optional<Manual> manual, ManualTagInsertRequestDTO manualTagInsertRequestDTO) {

        return ManualTag.builder()
                .manual(manual.get())
                .tagContent(manualTagInsertRequestDTO.tagContent)
                .build();

    } // toEntity(Optional<Manual> manual, ManualTagInsertRequestDTO manualTagInsertRequestDTO) 끝

} // class 끝
