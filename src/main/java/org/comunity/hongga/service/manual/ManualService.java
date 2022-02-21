package org.comunity.hongga.service.manual;

import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.manual.ManaualTagDTO;
import org.comunity.hongga.model.dto.request.manual.ManualImageDTO;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.ManualImage;
import org.comunity.hongga.model.entity.manual.ManualTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 사용 설명서 관련 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.21 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 주니하랑, 1.0.0, 2022.02.21 최초 작성
 * @See ""
 * @see <a href="코드로 배우는 스프링 부트 웹 프로젝트 P.419"></a>
 */


public interface ManualService {

    DefaultResponse<Long> writeManual(ManualWriteRequestDTO manualWriteRequestDTO, Long memberNo);

    default Map<String, Object> dtoToEntity(ManualWriteRequestDTO manualWriteRequestDTO) {

        HashMap<String, Object> entityMap = new HashMap<>();

        Manual manual = Manual.builder()
                .title(manualWriteRequestDTO.getTitle())
                .content(manualWriteRequestDTO.getContent())
                .build();

        entityMap.put("manual", manual);

//        ManualTag manualTag = ManualTag.builder()
//                .tagContent0(manualWriteRequestDTO.getTagContent0())
//                .tagContent1(manualWriteRequestDTO.getTagContent1())
//                .tagContent2(manualWriteRequestDTO.getTagContent2())
//                .tagContent3(manualWriteRequestDTO.getTagContent3())
//                .tagContent4(manualWriteRequestDTO.getTagContent4())
//                .tagContent5(manualWriteRequestDTO.getTagContent5())
//                .tagContent6(manualWriteRequestDTO.getTagContent6())
//                .tagContent7(manualWriteRequestDTO.getTagContent7())
//                .tagContent8(manualWriteRequestDTO.getTagContent8())
//                .tagContent9(manualWriteRequestDTO.getTagContent9())
//                .build();

//        entityMap.put("manualTag", manualTag);

        List<ManaualTagDTO> tagDTOLIST = manualWriteRequestDTO.getTagDTOLIST();

        // TagDTO 처리
        if (tagDTOLIST != null && tagDTOLIST.size() > 0) {

            tagDTOLIST.stream().map(manaualTagDTO -> {

                return ManualTag.builder()
                        .tagContent(manaualTagDTO.getTagContent())
                        .build();

            }).collect(Collectors.toList());

            entityMap.put("tagList", tagDTOLIST);

        } // if (tagDTOLIST != null && tagDTOLIST.size() > 0) 끝

        List<ManualImageDTO> imageDTOLIST = manualWriteRequestDTO.getImageDTOLIST();

        // MovieImageDTO 처리
        if (imageDTOLIST != null && imageDTOLIST.size() > 0) {

            List<ManualImage> manualImageList = imageDTOLIST.stream().map(manualImageDTO -> {

                return ManualImage.builder()
                        .path(manualImageDTO.getPath())
                        .imgName(manualImageDTO.getImaName())
                        .uuid(manualImageDTO.getUuid())
                        .manual(manual)
                        .build();

            }).collect(Collectors.toList());

            entityMap.put("imgList", manualImageList);

        } // if (imageDTOLIST != null && imageDTOLIST.size() > 0) 끝

        return entityMap;
    } // dtoToEntity(ManualWriteRequestDTO manualWriteRequestDTO) 끝

} // interface 끝
