package org.comunity.hongga.service.manual;

import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.manual.ManualImageDTO;
import org.comunity.hongga.model.dto.request.manual.ManualTagDTO;
import org.comunity.hongga.model.dto.request.manual.ManualWriteAndUpdateRequestDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListContentSearchResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListSearchResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListTagContentSearchResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.ManualImage;
import org.comunity.hongga.model.entity.manual.ManualTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 사용 설명서 관련 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.0.1, 2022.02.16 Tag 등록 추가
 *    주니하랑, 1.1.0, 2022.02.18 목록 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 *    주니하랑, 1.2.0, 2022.02.18 목록 조회, 상세 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 *    주니하랑, 1.2.1, 2022.02.19 수정 기능 구현
 *    주니하랑, 1.2.2, 2022.02.19 수정 기능 Tag로 인한 Refactoring
 *    주니하랑, 1.3.0, 2022.02.21 사진 등록 처리로 인한 Refactoring
 *    주니하랑, 1.3.1, 2022.02.25 상세 조회 기능 구현을 위한 Refactoring
 *    주니하랑, 1.3.2, 2022.02.25 삭제 기능 구현을 위한 Refactoring (Image 삭제 처리)
 *    주니하랑, 1.4.0, 2022.02.28 검색 기능(제목, 제목+내용, TAG) 구현
 * </pre>
 *
 * @author 주니하랑
 * @version 1.4.0, 2022.02.28 검색 기능(제목, 제목+내용, TAG) 구현
 * @See ""
 * @see <a href=""></a>
 */

public interface ManualService {

    /**
     * 글 등록
     * @param manualWriteAndUpdateRequestDTO - Client에서 입력한 값을 담은 DTO
     * @param memberNo - 글 작성 이용자 고유 번호
     * @return DefaultResponse<Long> - 응답 관련 정리 해둔 Class를 통해 작성된 게시글의 Manual 고유 번호를 반환(DB에 PK값)
     */

    DefaultResponse<Long> writeManual(ManualWriteAndUpdateRequestDTO manualWriteAndUpdateRequestDTO, Long memberNo);


    /**
     * 전체 조회 (목록 조회)
     * @param pageable - Paging 처리를 위한 객체
     * @return DefaultResponse<Page<ManualListSearchResponseDTO>> - DB에서 조회된 게시글 목록을 페이징 처리하여 반환
     * @see ""
     */

    DefaultResponse<Page<ManualListSearchResponseDTO>> manualListSearch(Pageable pageable);

    /**
     * 상세 조회
     * @param manualNo - 검색을 위한 게시글 고유 번호
     * @return DefaultResponse<ManualDetailResponseDTO> - DB에서 조회된 게시글 상세 정보 반환
     * @see ""
     */

    DefaultResponse<ManualDetailResponseDTO> manualDetailSearch(Long manualNo);

    /**
     * 게시글 수정
     * @param manualNo - 수정 대상 게시글 고유 번호
     * @param memberNo - 수정 요청 회원 고유 번호
     * @return DefaultResponse<Long> - 대상 게시글 고유 번호 반환
     * @see ""
     */

    DefaultResponse<Long> updateManual(ManualWriteAndUpdateRequestDTO manualWriteAndUpdateRequestDTO, Long manualNo, Long memberNo);

    /**
     * 게시글 삭제
     * @param manualNo - 검색을 위한 게시글 고유 번호
     * @return DefaultResponse - 삭제 관련 처리에 대한 HTTP 응답에 맞는 코드와 메시지 전달
     * @see ""
     */

    DefaultResponse deleteManual(Long manualNo, Long memberNo);

    /**
     * 제목으로 게시물 검색
     * @param title - 이용자가 검색 요청한 제목 일부분 검색어
     * @return DefaultResponse<Page<ManualListSearchResponseDTO>> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    DefaultResponse<Page<ManualListSearchResponseDTO>> titleSearch(String title, Pageable pageable);

    /**
     * 내용으로 게시물 검색
     * @param content - 이용자가 검색 요청한 내용 일부분 검색어
     * @return DefaultResponse<Page<ManualListSearchResponseDTO>> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    DefaultResponse<Page<ManualListContentSearchResponseDTO>> contentSearch(String content, Pageable pageable);

    /**
     * 제목 혹은 내용으로 게시물 검색
     * @param query - 이용자가 검색 요청한 제목 혹은 내용 일부분 검색어
     * @return DefaultResponse<Page<ManualListSearchResponseDTO>> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    DefaultResponse<Page<ManualListContentSearchResponseDTO>> contentTitleSearch(String query, Pageable pageable);

    /**
     * TAG로 게시물 검색
     * @param tagContent - 이용자가 검색 요청한 제목 혹은 내용 일부분 검색어
     * @return DefaultResponse<Page<ManualListSearchResponseDTO>> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    DefaultResponse<Page<ManualListTagContentSearchResponseDTO>> contentTagSearch(String tagContent, Pageable pageable);


    /**
     * DTO Type 객체 Entity Type 객체로 변환
     * @param manualWriteAndUpdateRequestDTO - 게시글 작성을 위해 요청자가 입력한 게시글 내용을 담은 DTO Type 객체
     * @return Map<String, Object> - 여러 건에 사진과 TAG를 함께 담기 위해 Map으로 값을 받아 반환
     * @see ""
     */

    default Map<String, Object> dtoToEntity(ManualWriteAndUpdateRequestDTO manualWriteAndUpdateRequestDTO) {

        HashMap<String, Object> entityMap = new HashMap<>();

        Manual manual = Manual.builder()
                .title(manualWriteAndUpdateRequestDTO.getTitle())
                .content(manualWriteAndUpdateRequestDTO.getContent())
                .build();

        entityMap.put("manual", manual);

        List<ManualImageDTO> imageDTOLIST = manualWriteAndUpdateRequestDTO.getImageDTOLIST();

        // MovieImageDTO 처리
        if (imageDTOLIST != null && imageDTOLIST.size() > 0) {

            List<ManualImage> manualImageList = imageDTOLIST.stream().map(manualImageDTO -> {

                return ManualImage.builder()
                        .path(manualImageDTO.getPath())
                        .imgName(manualImageDTO.getImgName())
                        .uuid(manualImageDTO.getUuid())
                        .manual(manual)
                        .build();

            }).collect(Collectors.toList());

            entityMap.put("imgList", manualImageList);

        } // if (imageDTOLIST != null && imageDTOLIST.size() > 0) 끝

        List<ManualTagDTO> tagDTOLIST = manualWriteAndUpdateRequestDTO.getTagDTOLIST();

        // TagDTO 처리
        if (tagDTOLIST != null && tagDTOLIST.size() > 0) {

            List<ManualTag> manualTagList = tagDTOLIST.stream().map(manualTagDTO -> {

                return ManualTag.builder()
                        .tagContent("#"+manualTagDTO.getTagContent())       // Hash Tag 처리를 위한 문자열 # 추가
                        .manual(manual)
                        .build();

            }).collect(Collectors.toList());

            entityMap.put("tagList", manualTagList);

        } // if (tagDTOLIST != null && tagDTOLIST.size() > 0) 끝

        return entityMap;
    } // dtoToEntity(ManualWriteRequestDTO manualWriteRequestDTO) 끝

    /**
     * Entity Type 객체 DTO Type 객체 로 변환
     * @param manual - ManualNo를 통해 DB에서 검색된 게시글 제목과 내용
     * @param manualImageList - ManualNo를 통해 DB에서 검색된 게시글 사진 일체
     * @param manualTagList - ManualNo를 통해 DB에서 검색된 게시글 TAG 일체
     * @return ManualDetailResponseDTO - Front 단에서 받게 하기 위해 DTO로 변환하여 반환
     * @see ""
     */

    default ManualDetailResponseDTO entitiesToDTO(Manual manual, List<ManualImage> manualImageList, List<ManualTag> manualTagList) {

        ManualDetailResponseDTO manualDetailResponseDTO = ManualDetailResponseDTO.builder()
                .manualNo(manual.getManualNo())
                .title(manual.getTitle())
                .createAt(manual.getCreateAt())
                .updateAt(manual.getUpdateAt())
                .content(manual.getContent())
                .build();

        List<ManualImageDTO> manualImageDTOList = manualImageList.stream().map(manualImage -> {
            return ManualImageDTO.builder()
                    .imgName(manualImage.getImgName())
                    .path(manualImage.getPath())
                    .uuid(manualImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        List<ManualTagDTO> manualTagDTOList = manualTagList.stream().map(manualTag -> {
            return ManualTagDTO.builder()
                    .tagContent(manualTag.getTagContent())
                    .build();
        }).collect(Collectors.toList());

        manualDetailResponseDTO.setImageDTOList(manualImageDTOList);
        manualDetailResponseDTO.setManualTagDTOList(manualTagDTOList);

        return manualDetailResponseDTO;

    } // entitiesToDTO() 끝
} // interface 끝
