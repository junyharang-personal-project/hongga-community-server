package org.comunity.hongga.model.dto.request.manual.Image;

import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.ManualImage;

import java.util.Optional;

/**
 * 사용 설명서 사진 등록 요청 관련 DTO
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

public class ManualImageInsertRequestDTO {

    private String uuid;
    private String imgName;
    private String path;

    public ManualImage toEntity(Optional<Manual> manual, ManualImageInsertRequestDTO manualImageInsertRequestDTO) {

        return ManualImage.builder()
                .manual(manual.get())
                .uuid(manualImageInsertRequestDTO.uuid)
                .imgName(manualImageInsertRequestDTO.imgName)
                .path(manualImageInsertRequestDTO.path)
                .build();

    } // toEntity(Optional<Manual> manual, ManualImageInsertRequestDTO manualImageInsertRequestDTO) 끝

} // class 끝
