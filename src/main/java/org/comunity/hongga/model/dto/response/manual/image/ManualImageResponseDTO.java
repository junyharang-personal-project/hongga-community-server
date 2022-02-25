package org.comunity.hongga.model.dto.response.manual.image;

/**
 * 사용 설명서 사진 등록 응답 관련 DTO
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

public class ManualImageResponseDTO {

    private Long imageNo;
    private Long manualNo;

    private String uuid;
    private String imgName;
    private String path;

    public ManualImageResponseDTO(ManualImageResponseDTO manualImageResponseDTO) {

        this.imageNo = manualImageResponseDTO.imageNo;
        this.manualNo = manualImageResponseDTO.manualNo;

        this.uuid = manualImageResponseDTO.uuid;
        this.imgName = manualImageResponseDTO.imgName;
        this.path = manualImageResponseDTO.path;

    } // ManualImageResponseDTO(ManualImageResponseDTO manualImageResponseDTO) 끝

} // class 끝
