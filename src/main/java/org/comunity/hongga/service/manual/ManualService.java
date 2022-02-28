package org.comunity.hongga.service.manual;

import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.manual.ManualUpdateRequestDTO;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListContentSearchResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListSearchResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

import java.util.List;

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
 * </pre>
 *
 * @author 주니하랑
 * @version 1.3.2, 2022.02.25 삭제 기능 구현을 위한 Refactoring (Image 삭제 처리)
 * @See ""
 * @see <a href=""></a>
 */

public interface ManualService {

    /**
     * 글 등록
     * @param manualWriteRequestDTO - Client에서 입력한 값을 담은 DTO
     * @param memberNo - 글 작성 이용자 고유 번호
     * @return DefaultResponse<Long> - 응답 관련 정리 해둔 Class를 통해 작성된 게시글의 Manual 고유 번호를 반환(DB에 PK값)
     */

    DefaultResponse<Long> writeManual(ManualWriteRequestDTO manualWriteRequestDTO, Long memberNo);


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

    DefaultResponse<List<ManualDetailResponseDTO>> manualDetailSearch(Long manualNo);

    /**
     * 게시글 수정
     * @param manualNo - 수정 대상 게시글 고유 번호
     * @param memberNo - 수정 요청 회원 고유 번호
     * @return DefaultResponse<Long> - 대상 게시글 고유 번호 반환
     * @see ""
     */

    DefaultResponse<Long> updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo);

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
     * 내용으로 게시물 검색
     * @param query - 이용자가 검색 요청한 제목 혹은 내용 일부분 검색어
     * @return DefaultResponse<Page<ManualListSearchResponseDTO>> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    DefaultResponse<Page<ManualListContentSearchResponseDTO>> contentTitleSearch(String query, Pageable pageable);
} // interface 끝
