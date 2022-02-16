package org.comunity.hongga.repository.manual.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.dto.response.manual.ManualListResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.comunity.hongga.model.entity.manual.QManual.manual;
import static org.comunity.hongga.model.entity.member.QMember.member;

/**
 * Manual TAG Query dsl Repository
 * <pre>
 * <b>History:/b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.16 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

/**
@param pageable - 페이징 처리를 위한 객체, memberNo - 회원 고유 번호
 */

@RequiredArgsConstructor @Slf4j
@Repository public class ManualQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public Page<ManualListResponseDTO> findAllWithFetchJoin (Pageable pageable) {    /* 전체 조회 / 페이징 처리 */

        log.info("ManualService에서 넘겨 받은 요청 값 확인 : " + pageable.toString());

        log.debug("ManualQuerydslRepository가 동작 하였습니다!");
        log.debug("메뉴얼 게시글 전체 조회 요청으로 findAllWithFetchJoin (Pageable pageable, Long memberNo)가 호출 되었습니다!");

        List<ManualListResponseDTO> manualList = jpaQueryFactory
                .select(Projections.constructor(ManualListResponseDTO.class,
                        manual.manualNo.as("manualNo"),
                        member.nickname,
                        manual.title))

                .from(manual)
                .innerJoin(manual.writer, member)
                .orderBy(manual.manualNo.desc())
                .fetch();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), manualList.size());

        log.info("조회 된 결과를 통해 한 페이지 당 10개씩 페이징 처리 하여 반환하겠습니다!");

        return new PageImpl<>(manualList.subList(start, end), pageable, manualList.size());

    } // findAllWithFetchJoin (Pageable pageable, Long memberNo) 끝

} // class 끝