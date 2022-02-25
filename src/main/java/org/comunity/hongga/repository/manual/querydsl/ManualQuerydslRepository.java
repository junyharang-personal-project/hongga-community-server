package org.comunity.hongga.repository.manual.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.dto.request.manual.ManualUpdateRequestDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListSearchResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.comunity.hongga.model.entity.manual.QManual.manual;
import static org.comunity.hongga.model.entity.manual.QManualImage.manualImage;
import static org.comunity.hongga.model.entity.manual.QManualTag.manualTag;
import static org.comunity.hongga.model.entity.member.QMember.member;

/**
 * Manual TAG Query dsl Repository
 * <pre>
 * <b>History:/b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 *    주니하랑, 1.1.0, 2022.02.18 목록 조회 동적 Query용 Query dsl 대신 JPQL로 변경(주석 처리)
 *    주니하랑, 1.2.0, 2022.02.20 Tag 동시 처리를 위한 수정 관련 기능 추가
 *    주니하랑, 1.2.1, 2022.02.22 목록 조회 동적 Query용 Query dsl으로 다시 변환
 * </pre>
 *
 * @author 주니하랑
 * @version 주니하랑, 1.2.1, 2022.02.22 목록 조회 동적 Query용 Query dsl으로 다시 변환
 * @See ""
 * @see <a href=""></a>
 */

/**
@param // pageable - 페이징 처리를 위한 객체, memberNo - 회원 고유 번호
 */

@RequiredArgsConstructor @Slf4j
@Repository public class ManualQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    /**
     * 전체 목록 조회 Method
     * @param pageable 페이징 처리를 위한 객체
     * @return Page<ManualListSearchResponseDTO> 조회된 게시물 목록을 페이징 처리 하여 반환
     */

    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되, 조회 기능만 남겨 조회 속도 개선을 위해 사용
    public Page<ManualListSearchResponseDTO> findAllWithMemberNickname (Pageable pageable) {    /* 전체 조회 / 페이징 처리 */

        log.info("ManualService에서 넘겨 받은 요청 값 확인 : " + pageable.toString());

        log.info("ManualQuerydslRepository가 동작 하였습니다!");
        log.info("메뉴얼 게시글 전체 조회 요청으로 findAllWithFetchJoin (Pageable pageable, Long memberNo)가 호출 되었습니다!");

        List<ManualListSearchResponseDTO> manualList = jpaQueryFactory
                .select(Projections.constructor(ManualListSearchResponseDTO.class,
                        manual.manualNo.as("manualNo"),
                        member.nickname,
                        manual.title,
                        manual.createAt,
                        manual.updateAt
                ))
                .from(manual)
                .innerJoin(manual.writer, member)
                .orderBy(manual.manualNo.desc())
                .fetch();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), manualList.size());

        log.info("조회 된 결과를 통해 한 페이지 당 10개씩 페이징 처리 하여 반환하겠습니다!");

        return new PageImpl<>(manualList.subList(start, end), pageable, manualList.size());

    } // findAllWithFetchJoin (Pageable pageable, Long memberNo) 끝

    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되, 조회 기능만 남겨 조회 속도 개선을 위해 사용
    public Optional<List<ManualDetailResponseDTO>> findByManualNo(Long manualNo) {

        List<ManualDetailResponseDTO> result = jpaQueryFactory
                .select(Projections.constructor(ManualDetailResponseDTO.class,
                        manual.manualNo,
                        manual.title,
                        member.nickname,
                        manual.createAt,
                        manual.updateAt,
                        manual.content,
                        manualImage.uuid,
                        manualImage.path,
                        manualImage.imgName,
                        manualTag.tagContent
                ))
                .from(manual)
                .innerJoin(manual.writer, member)
                .leftJoin(manualImage)
                .on(manualImage.manual.manualNo.eq(manual.manualNo))
                .leftJoin(manualTag)
                .on(manualTag.manual.manualNo.eq(manual.manualNo))
                .where(manual.manualNo.eq(manualNo))
                .fetch();

        return Optional.ofNullable(result);

    } // findByManualNo(Long manualNo) 끝
} // class 끝
