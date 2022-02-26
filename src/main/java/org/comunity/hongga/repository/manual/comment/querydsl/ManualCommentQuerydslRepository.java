package org.comunity.hongga.repository.manual.comment.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.dto.request.manual.comment.ManualCommentUpdateRequestDTO;
import org.comunity.hongga.model.dto.response.manual.comment.ManualCommentListSearchResponseDTO;
import org.comunity.hongga.model.entity.manual.comment.ManualComment;
import org.comunity.hongga.model.entity.manual.comment.QManualComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.comunity.hongga.model.entity.manual.QManual.manual;
import static org.comunity.hongga.model.entity.manual.comment.QManualComment.manualComment;
import static org.comunity.hongga.model.entity.member.QMember.member;

@RequiredArgsConstructor @Slf4j
@Repository public class ManualCommentQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    /**
     * 전체 조회 (목록 조회)
     * @param manualNo - 댓글이 종속된 게시물 고유 번호
     * @param pageable - Paging 처리를 위한 객체
     * @return Page<ManualCommentListSearchResponseDTO> - DB에서 조회된 게시글 목록을 페이징 처리하여 반환
     * @see ""
     */

    public Page<ManualCommentListSearchResponseDTO> findAllWithManualAndWriter(Long manualNo, Pageable pageable) {

        log.info("ManualCommentQuerydslRepository의 findAllWithManualAndWriter(Long manualNo, Pageable pageable)이 호출 되었습니다!");

        List<ManualCommentListSearchResponseDTO> manualCommentList = jpaQueryFactory
                .select(Projections.constructor(ManualCommentListSearchResponseDTO.class,
                        manualComment.manualCommentNo,
                        manual.manualNo,
                        member.nickname,
                        manualComment.commentContent,
                        manualComment.createAt,
                        manualComment.updateAt))
                .from(manualComment)
                .innerJoin(manualComment.writer, member)
                .innerJoin(manualComment.manual, manual)
                .where(manualComment.manual.manualNo.eq(manualNo))
                .orderBy(manualComment.manualCommentNo.desc())
                .fetch();

        int start = (int) pageable.getOffset();
        int ent = Math.min(start + pageable.getPageSize(), manualCommentList.size());

        return new PageImpl<>(manualCommentList.subList(start, ent), pageable, manualCommentList.size());

    } // findAllWithManualAndWriter(Long manualNo, Pageable pageable) 끝

    @Transactional
    public void updateManualCommnet(ManualCommentUpdateRequestDTO manualCommentUpdateRequestDTO, Long manualCommentNo, Long memberNo) {

        log.info("ManualCommentQuerydslRepository의 manualCommentUpdateRequestDTO, String commentContent, Long memberNo)이 호출 되었습니다!");

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, manualComment);

        updateClause
                .where(manualComment.manualCommentNo.eq(manualCommentNo), manualComment.writer.memberNo.eq(memberNo))
                .set(manualComment.commentContent, manualCommentUpdateRequestDTO.getCommentContent())
                .set(manualComment.updateAt, manualCommentUpdateRequestDTO.getUpdateAt())
                .execute();

    } // updateManualCommnet(ManualCommentUpdateRequestDTO manualCommentUpdateRequestDTO, String commentContent, Long memberNo) 끝

    public Optional<ManualComment> findByManualCommentId(Long manualCommentNo) {

        log.info("ManualCommentQuerydslRepository의 findByManualCommentId(Long manualCommentNo)이 호출 되었습니다!");

        ManualComment manualComment = jpaQueryFactory
                .selectFrom(QManualComment.manualComment)
                .innerJoin(QManualComment.manualComment.writer, member)
                .fetchJoin()
                .where(QManualComment.manualComment.manualCommentNo.eq(manualCommentNo))
                .fetchOne();

        return Optional.ofNullable(manualComment);

    } // findByManualCommentId(Long manualCommentNo) 끝
} // class 끝
