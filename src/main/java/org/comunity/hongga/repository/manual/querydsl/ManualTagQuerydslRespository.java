package org.comunity.hongga.repository.manual.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.dto.request.manual.ManualWriteAndUpdateRequestDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.comunity.hongga.model.entity.manual.QManualTag.manualTag;

/**
 * Manual TAG Query dsl Repository
 * <pre>
 * <b>History:/b>
 *    주니하랑, 1.0.0, 2022.02.25 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 주니하랑, 1.0.0, 2022.02.25 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Repository public class ManualTagQuerydslRespository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    /**
     * 게시글 Tag 수정 Method
     * @param manualWriteAndUpdateRequestDTO 수정 요청 이용자가 게시글 수정 내용에 대해 수정한 값을 담은 DTO 객체
     * @param manualNo 수정 대상 게시글 고유 번호
     */

   @Transactional public void updateManualTag(ManualWriteAndUpdateRequestDTO manualWriteAndUpdateRequestDTO, Long manualNo) {

        log.info("ManualTagQuerydslRespository의 updateManualTag(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo)가 호출 되었습니다!");

        log.info("이용자의 요청에 따라 TAG 내용을 수정 합니다!");

        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(entityManager, manualTag);

        jpaUpdateClause.where(manualTag.manual.manualNo.eq(manualNo))
                .set(manualTag.tagContent, manualWriteAndUpdateRequestDTO.getTagContent())
                .execute();

    } // updateManualTag(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo) 끝
} // class 끝
