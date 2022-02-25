package org.comunity.hongga.service.manual.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.manual.comment.ManualCommentWriteRequestDTO;
import org.comunity.hongga.model.dto.response.manual.comment.ManualCommentWriterResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.manual.ManualRepository;
import org.comunity.hongga.repository.manual.comment.ManualCommentRepository;
import org.comunity.hongga.repository.member.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 메뉴얼 댓글 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.26 최초 작성
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.26 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Service public class ManualCommentServiceImpl implements ManualCommentService{

    private final ManualCommentRepository commentRepository;
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

            return DefaultResponse.response(HttpStatus.NOT_FOUND.value(), "게시글 없음");

        } // if (dbInManual.isEmpty()) 끝

        log.info("댓글 등록 요청자가 정상 가입자인지 DB에서 조회 하겠습니다!");

        Optional<Member> dbInMember = memberRepository.findById(memberNo);

        if (dbInMember.isEmpty()) {

            log.info("비 정상적인 이용자 입니다! 보안을 위해 401 대신 404 Code와 함께 \"게시글 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(HttpStatus.NOT_FOUND.value(), "게시글 없음");

        } // if (dbInMember.isEmpty()) 끝

        log.info("요청자가 보낸 댓글 내용이 비어 있는지 검사 하겠습니다!");

        if (writeRequestDTO == null) {

            log.info("등록 요청한 댓글 내용이 비어 있습니다! 204 Code와 함께 \"등록 실패\" 반환 하겠습니다!");

            return DefaultResponse.response(HttpStatus.NO_CONTENT.value(), "등록 실패");

        } // if (writeRequestDTO == null) 끝


        commentRepository.save(writeRequestDTO.toEntity(dbInManual, dbInMember, writeRequestDTO));

        return DefaultResponse.response(HttpStatus.OK.value(), "등록 성공");

    } // writeManualComment(ManualCommentWriteRequestDTO writeRequestDTO, Long manualNo, Long memberNo) 끝
} // class 끝
