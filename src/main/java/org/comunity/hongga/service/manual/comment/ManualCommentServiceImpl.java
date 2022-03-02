package org.comunity.hongga.service.manual.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.Pagination;
import org.comunity.hongga.constant.ResponseCode;
import org.comunity.hongga.model.dto.request.manual.comment.ManualCommentUpdateRequestDTO;
import org.comunity.hongga.model.dto.request.manual.comment.ManualCommentWriteRequestDTO;
import org.comunity.hongga.model.dto.response.manual.comment.ManualCommentListSearchResponseDTO;
import org.comunity.hongga.model.dto.response.manual.comment.ManualCommentWriterResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.comment.ManualComment;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.manual.ManualRepository;
import org.comunity.hongga.repository.manual.comment.ManualCommentRepository;
import org.comunity.hongga.repository.manual.comment.querydsl.ManualCommentQuerydslRepository;
import org.comunity.hongga.repository.member.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 메뉴얼 댓글 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.26 최초 작성
 *    주니하랑, 1.0.1, 2022.02.26 댓글 작성, 목록 조회 구현
 *    주니하랑, 1.0.2, 2022.02.26 댓글 수정 기능 구현
 *    주니하랑, 1.0.3, 2022.02.26 댓글 삭제 기능 구현
 *    주니하랑, 1.0.4, 2022.03.02 응답 코드 구체화로 인한 return문 수정
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.4, 2022.03.02 응답 코드 구체화로 인한 return문 수정
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Service public class ManualCommentServiceImpl implements ManualCommentService{

    private final ManualCommentRepository commentRepository;
    private final ManualCommentQuerydslRepository commentQuerydslRepository;
    private final MemberRepository memberRepository;
    private final ManualRepository manualRepository;

    /**
     * 댓글 등록
     * @param writeRequestDTO - Client에서 입력한 값을 담은 DTO
     * @param manualNo - 해당 댓글이 종속될 게시글의 고유 번호
     * @param memberNo - 글 작성 이용자 고유 번호
     * @return DefaultResponse<ManualCommentWriterResponseDTO> - 등록 된 댓글 내용 객체
     */


    @Override
    public DefaultResponse<ManualCommentWriterResponseDTO> writeManualComment(ManualCommentWriteRequestDTO writeRequestDTO, Long manualNo, Long memberNo) {
        log.info("ManualCommentServiceImpl의 writeManualComment(ManualCommentWriteRequestDTO writeRequestDTO, Long manualNo, Long memberNo)가 호출 되었습니다!");

        log.info("이용자가 등록 요청한 대상 게시글이 존재하는지 DB에서 조회 하겠습니다!");

        Optional<Manual> dbInManual = manualRepository.findById(manualNo);

        if (dbInManual.isEmpty()) {

            log.info("게시글이 존재 하지 않습니다! 404 Code와 함께 \"게시글 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.NotFoundResult.getCode(), ResponseCode.NotFoundResult.getMessageKo(), ResponseCode.NotFoundResult.getMessageEn());

        } // if (dbInManual.isEmpty()) 끝

        log.info("댓글 등록 요청자가 정상 가입자인지 DB에서 조회 하겠습니다!");

        Optional<Member> dbInMember = memberRepository.findById(memberNo);

        if (dbInMember.isEmpty()) {

            log.info("비 정상적인 이용자 입니다! 보안을 위해 401 대신 404 Code와 함께 \"게시글 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.NotFoundResult.getCode(), ResponseCode.NotFoundResult.getMessageKo(), ResponseCode.NotFoundResult.getMessageEn());

        } // if (dbInMember.isEmpty()) 끝

        log.info("요청자가 보낸 댓글 내용이 비어 있는지 검사 하겠습니다!");

        if (writeRequestDTO == null) {

            log.info("등록 요청한 댓글 내용이 비어 있습니다! 204 Code와 함께 \"등록 실패\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.NotFoundFile.getCode(), ResponseCode.NotFoundFile.getMessageKo(), ResponseCode.NotFoundFile.getMessageEn());

        } // if (writeRequestDTO == null) 끝


        commentRepository.save(writeRequestDTO.toEntity(dbInManual, dbInMember, writeRequestDTO));

        return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn());

    } // writeManualComment(ManualCommentWriteRequestDTO writeRequestDTO, Long manualNo, Long memberNo) 끝


    /**
     * 전체 조회 (목록 조회)
     * @Param manualNo - 댓글의 의존 대상 게시글 고유 번호
     * @param pageable - Paging 처리를 위한 객체
     * @return DefaultResponse<Page<ManualCommentListSearchResponseDTO>> - DB에서 조회된 댓글 목록을 페이징 처리하여 반환
     * @see ""
     */

    @Override
    public DefaultResponse<Page<ManualCommentListSearchResponseDTO>> manualListSearch(Long manualNo, Pageable pageable) {

        log.info("ManualCommentServiceImpl의 manualListSearch(Long manualNo, Pageable pageable)가 호출 되었습니다!");

        Page<ManualCommentListSearchResponseDTO> allWithManualAndWriter = commentQuerydslRepository.findAllWithManualAndWriter(manualNo, pageable);

        log.info("DB를 통해 메뉴얼 댓글 목록을 모두 조회 하겠습니다!");

        log.info("조회된 결과 값 : " + allWithManualAndWriter.toString());

        if (allWithManualAndWriter.getTotalElements() == 0) {

            log.info("이런! DB에 해당 게시글에 댓글이 하나도 없네요!");

            return DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn());

        } // if (allWithManualAndWriter.getTotalElements() == 0) 끝

        return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), allWithManualAndWriter, new Pagination(allWithManualAndWriter));

    } // manualListSearch(Long manualNo, Pageable pageable) 끝환


    /**
     * 댓글 수정
     * @param manualCommentUpdateRequestDTO - 수정할 내용이 담긴 DTO
     * @param manualNo - 댓글이 의존된 게시글 고유 번호
     * @param manualCommentNo - 수정 대상 댓글 고유 번호
     * @param memberNo - 수정 요청 이용자의 고유 번호
     * @return DefaultResponse<Long> - HTTP 응답 처리 및 댓글 수정 처리에 대한 댓글 고유 번호 반환
     * @see ""
     */

    @Override
    public DefaultResponse<Long> updateManualComment(ManualCommentUpdateRequestDTO manualCommentUpdateRequestDTO, Long manualNo, Long manualCommentNo, Long memberNo) {

        log.info("ManualCommentServiceImpl의 updateManualComment(ManualCommentUpdateRequestDTO manualCommentUpdateRequestDTO, Long manualNo, Long manualCommentNo, Long memberNo)");
        log.info("DB에서 이용자가 수정 요청한 댓글이 존재하는지 찾아 보겠습니다!");

        Optional<ManualComment> dbInComment = commentQuerydslRepository.findByManualCommentId(manualCommentNo);

        log.info("DB에서 조회된 결과 값 : " + dbInComment.get());

        if (dbInComment.isEmpty()) {

            log.info("이용자가 수정 요청한 댓글이 DB에 존재하지 않습니다!");
            log.info("204 Code와 함께 \"댓글 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn(), manualCommentNo);

        } // if (dbInComment.isEmpty()) 끝

        log.info("DB에서 찾은 댓글 값이 매개 변수 중 게시글 고유 번호, 댓글 고유 번호, 수정 요청자 고유 번호와 일치하는지 검사하겠습니다!");

        return dbInComment.filter(manualComment -> manualComment.getManual().getManualNo().equals(manualNo))
                          .filter(manualComment -> manualComment.getManualCommentNo().equals(manualCommentNo))
                          .filter(manualComment -> manualComment.getWriter().getMemberNo().equals(memberNo))
                .map(manualComment -> {

                    log.info("DB에서 찾은 댓글 값이 매개 변수 내용과 일치 합니다! DB에 수정 요청을 하겠습니다!");

                    commentQuerydslRepository.updateManualCommnet(manualCommentUpdateRequestDTO, manualComment.getManualCommentNo(), manualComment.getWriter().getMemberNo());

                    log.info("수정이 완료 되었습니다! \n 200 Code와 함께 \"수정 성공\" 반환 하겠습니다!");

                    return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), manualCommentNo);

                }).orElseGet(() -> DefaultResponse.response(ResponseCode.ServerError.getCode(), ResponseCode.ServerError.getMessageKo(), ResponseCode.ServerError.getMessageEn()));

    } // updateManualComment(ManualCommentUpdateRequestDTO manualCommentUpdateRequestDTO, Long manualNo, Long manualCommentNo, Long memberNo) 끝제

    /**
     * 댓글 삭제
     * @param manualNo - 댓글이 의존된 게시글 고유 번호
     * @param manualCommentNo - 삭제 대상 댓글 고유 번호
     * @param memberNo - 삭제 요청 이용자의 고유 번호
     * @return DefaultRespons<Long> - HTTP 응답 처리 및 댓글 수정 처리에 대한 댓글 고유 번호 반환
     * @see ""
     */

    @Override
    public DefaultResponse<Long> deleteManualComment(Long manualNo, Long manualCommentNo, Long memberNo) {

        log.info("ManualCommentServiceImpl의 deleteManualComment(Long manualNo, Long manualCommentNo, Long memberNo)");
        log.info("DB에서 이용자가 수정 요청한 댓글이 존재하는지 찾아 보겠습니다!");

        Optional<ManualComment> dbInComment = commentQuerydslRepository.findByManualCommentId(manualCommentNo);

        log.info("DB에서 조회된 결과 값 : " + dbInComment.get());

        if (dbInComment.isEmpty()) {

            log.info("이용자가 삭제 요청한 댓글이 DB에 존재하지 않습니다!");
            log.info("204 Code와 함께 \"댓글 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn());

        } // if (dbInComment.isEmpty()) 끝

        log.info("DB에서 찾은 댓글 값이 매개 변수 중 게시글 고유 번호, 댓글 고유 번호, 삭제 요청자 고유 번호와 일치하는지 검사 하겠습니다!");

        return dbInComment.filter(manualComment -> manualComment.getManual().getManualNo().equals(manualNo))
                          .filter(manualComment -> manualComment.getWriter().getMemberNo().equals(memberNo))
                .map(manualComment -> {

                    log.info("DB에서 찾은 댓글 값이 매개 변수 내용과 일치 합니다! DB에 수정 요청을 하겠습니다!");

                    commentQuerydslRepository.deleteByManualCommentNo(manualComment.getManualCommentNo(), manualComment.getWriter().getMemberNo());

                    log.info("삭제가 완료 되었습니다! \n 200 Code와 함께 \"삭제 성공\" 반환 하겠습니다!");

                    return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), manualCommentNo);

                }).orElseGet(() -> DefaultResponse.response(ResponseCode.ServerError.getCode(), ResponseCode.ServerError.getMessageKo(), ResponseCode.ServerError.getMessageEn()));
    } // deleteManualComment(Long manualNo, Long manualCommentNo, Long memberNo) 끝
} // class 끝
