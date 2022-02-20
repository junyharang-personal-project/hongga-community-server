package org.comunity.hongga.repository.manual.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.dto.request.manual.ManualUpdateRequestDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.comunity.hongga.model.entity.manual.QManualTag.manualTag;

/**
 * Manual TAG Query dsl Repository
 * <pre>
 * <b>History:/b>
 *    주니하랑, 1.0.0, 2022.02.20 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.20 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Repository public class ManualTagQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

   @Transactional public void updateManualTag(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo) {

       log.debug("ManualQuerydslRepository가 동작 하였습니다!");
       log.debug("메뉴얼 Tag 수정 요청으로 updateManualTag(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo)가 호출 되었습니다!");

       log.info("ManualService에서 넘겨 받은 수정 사항 요청 값 제목과 내용 : " + manualUpdateRequestDTO.toString() + "게시글 번호 : " + manualNo);

       JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(entityManager, manualTag);

       jpaUpdateClause
               .where(manualTag.manual.manualNo.eq(manualNo))
               .set(manualTag.tagContent0, manualUpdateRequestDTO.getTagContent0())
               .set(manualTag.tagContent1, manualUpdateRequestDTO.getTagContent1())
               .set(manualTag.tagContent2, manualUpdateRequestDTO.getTagContent2())
               .set(manualTag.tagContent3, manualUpdateRequestDTO.getTagContent3())
               .set(manualTag.tagContent4, manualUpdateRequestDTO.getTagContent4())
               .set(manualTag.tagContent5, manualUpdateRequestDTO.getTagContent5())
               .set(manualTag.tagContent6, manualUpdateRequestDTO.getTagContent6())
               .set(manualTag.tagContent7, manualUpdateRequestDTO.getTagContent7())
               .set(manualTag.tagContent8, manualUpdateRequestDTO.getTagContent8())
               .set(manualTag.tagContent9, manualUpdateRequestDTO.getTagContent9())
               .execute();

   } // updateManualTag(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo) 끝

} //class 끝
