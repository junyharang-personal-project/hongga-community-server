package org.comunity.hongga.service.manual.comment;

import lombok.RequiredArgsConstructor;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.manual.comment.ManualCommentWriteRequestDTO;
import org.comunity.hongga.model.dto.response.manual.comment.ManualCommentListSearchResponseDTO;
import org.comunity.hongga.model.dto.response.manual.comment.ManualCommentWriterResponseDTO;
import org.comunity.hongga.repository.manual.ManualRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 메뉴얼 댓글 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.26 최초 작성
 *    주니하랑, 1.0.1, 2022.02.26 댓글 작성, 목록 조회 구현
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.26 댓글 작성, 목록 조회 구현
 * @See ""
 * @see <a href=""></a>
 */

@Service public interface ManualCommentService {

    /**
     * 댓글 등록
     * @param writeRequestDTO - Client에서 입력한 값을 담은 DTO
     * @param manualNo - 해당 댓글이 종속될 게시글의 고유 번호
     * @param memberNo - 글 작성 이용자 고유 번호
     * @return DefaultResponse<ManualCommentWriterResponseDTO> - 등록 된 댓글 내용 객체
     */

    DefaultResponse<ManualCommentWriterResponseDTO> writeManualComment(ManualCommentWriteRequestDTO writeRequestDTO, Long manualNo, Long memberNo);

    /**
     * 전체 조회 (목록 조회)
     * @param pageable - Paging 처리를 위한 객체
     * @return DefaultResponse<Page<Manual>> - DB에서 조회된 게시글 목록을 페이징 처리하여 반환
     * @see ""
     */
    DefaultResponse<Page<ManualCommentListSearchResponseDTO>> manualListSearch(Long manualNo, Pageable pageable);

} // class 끝
