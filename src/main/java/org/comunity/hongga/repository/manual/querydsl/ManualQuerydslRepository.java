package org.comunity.hongga.repository.manual.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.dto.request.manual.ManualUpdateRequestDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListContentSearchResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListSearchResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListTagContentSearchResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.QManual;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.QMember;
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
 * Manual Query dsl Repository
 * <pre>
 * <b>History:/b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 *    주니하랑, 1.1.0, 2022.02.18 목록 조회 동적 Query용 Query dsl 대신 JPQL로 변경(주석 처리)
 *    주니하랑, 1.2.0, 2022.02.20 Tag 동시 처리를 위한 수정 관련 기능 추가
 *    주니하랑, 1.2.1, 2022.02.22 목록 조회 동적 Query용 Query dsl으로 다시 변환
 *    주니하랑, 1.3.0, 2022.02.28 검색 기능(제목, 제목+내용, TAG) 구현
 * </pre>
 *
 * @author 주니하랑
 * @version 주니하랑, 1.3.0, 2022.02.28 검색 기능(제목, 제목+내용, TAG) 구현
 * @See ""
 * @see <a href=""></a>
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

    /**
     * 상세 조회 Method
     * @param manualNo 상세 조회 대상 게시글 고유 번호
     * @return Optional<List<ManualDetailResponseDTO>> 조회된 게시물 목록(Tag, Image는 N개)를 List에 담아 반환
     */

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

    /**
     * 게시글 수정 전 해당 게시글 조회를 위한 Method
     * @param manualNo 특정 게시글 조회를 위한 게시글 고유 번호
     * @return Optional<Manual> 조회 된 게시글 반환
     */

    @Transactional(readOnly = true)
    public Optional<Manual> findByManualNo(Long manualNo, Long memberNo) {

        Manual result = jpaQueryFactory
                .select(QManual.manual)
                .from(manual)
                .innerJoin(manual.writer, member)
                .where(QManual.manual.manualNo.eq(manualNo), QManual.manual.writer.memberNo.eq(memberNo))
                .fetchOne();

        return Optional.ofNullable(result);

    } // findByManualNo(Long manualNo, Long memberNo)


    /**
     * 게시글 수정 Method
     * @param manualUpdateRequestDTO 수정 요청 이용자가 제목, 내용에 대해 수정한 값을 담은 DTO 객체
     * @param manualNo 수정 대상 게시글 고유 번호
     * @param memberNo 수정 요청 이용자 고유 번호
     */

    @Transactional public void updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo) {

        log.info("ManualTagQuerydslRespository의 updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo)가 호출 되었습니다!");

        log.info("이용자의 요청에 따라 게시글 수정 합니다!");

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, manual);

        updateClause.where(manual.manualNo.eq(manualNo), manual.writer.memberNo.eq(memberNo))
                .set(manual.title, manualUpdateRequestDTO.getTitle())
                .set(manual.content, manualUpdateRequestDTO.getContent())
                .set(manual.updateAt, manualUpdateRequestDTO.getUpdateAt())
                .execute();

    } // updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo) 끝


    /**
     * 제목으로 게시물 검색
     * @param title - 이용자가 검색 요청한 제목 검색
     * @return Page<ManualListSearchResponseDTO> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    public Page<ManualListSearchResponseDTO> findByTitle(String title, Pageable pageable) {

        log.info("ManualQuerydslRepository의 findByTitle(String title, Pageable pageable)가 호출 되었습니다!");

        List<ManualListSearchResponseDTO> listSearchResponseDTOS = jpaQueryFactory
                .select(Projections.constructor(ManualListSearchResponseDTO.class,
                        manual.manualNo,
                        manual.title,
                        member.nickname,
                        manual.createAt,
                        manual.updateAt))

                .from(manual)
                .innerJoin(manual.writer, member)
                // containsIgnoreCase 대소문자 무시하고, 검색어 입력 받기 위해 사용
                .where(manual.title.containsIgnoreCase(title))
                .orderBy(manual.manualNo.desc())
                .fetch();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), listSearchResponseDTOS.size());

        return new PageImpl<>(listSearchResponseDTOS.subList(start, end), pageable, listSearchResponseDTOS.size());

    } // findByTitle(String title, Pageable pageable) 끝


    /**
     * 내용으로 게시물 검색
     * @param content - 이용자가 검색 요청한 내용 일부분 검색어
     * @return Page<ManualListSearchResponseDTO> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    public Page<ManualListContentSearchResponseDTO> findByContent(String content, Pageable pageable) {

        log.info("ManualQuerydslRepository의 findByContent(String content, Pageable pageable)가 호출 되었습니다!");

        List<ManualListContentSearchResponseDTO> listSearchResponseDTOS = jpaQueryFactory
                .select(Projections.constructor(ManualListContentSearchResponseDTO.class,
                        manual.manualNo,
                        manual.title,
                        member.nickname,
                        manual.createAt,
                        manual.updateAt,
                        manual.content))

                .from(manual)
                .innerJoin(manual.writer, member)
                .where(manual.content.containsIgnoreCase(content))
                .orderBy(manual.manualNo.desc())
                .fetch();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), listSearchResponseDTOS.size());

        return new PageImpl<>(listSearchResponseDTOS.subList(start, end), pageable, listSearchResponseDTOS.size());

    } // findByContent(String content, Pageable pageable) 끝


    /**
     * 제목 + 내용으로 게시물 검색
     * @param query - 이용자가 검색 요청한 제목 혹은 내용 일부분 검색어
     * @return Page<ManualListContentSearchResponseDTO> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    public Page<ManualListContentSearchResponseDTO> findByTitleOrContent(String query, Pageable pageable) {

        log.info("ManualQuerydslRepository의 findByTitleOrContent(String query, Pageable pageable)가 호출 되었습니다!");

        List<ManualListContentSearchResponseDTO> listContentSearchResponseDTOS = jpaQueryFactory
                .select(Projections.constructor(ManualListContentSearchResponseDTO.class,
                        manual.manualNo,
                        manual.title,
                        member.nickname,
                        manual.createAt,
                        manual.updateAt,
                        manual.content
                ))

                .from(manual)
                .innerJoin(manual.writer, member)
                .where(manual.title.containsIgnoreCase(query)
                        .or(manual.content.containsIgnoreCase(query)))
                .orderBy(manual.manualNo.desc())
                .fetch();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), listContentSearchResponseDTOS.size());

        return new PageImpl<>(listContentSearchResponseDTOS.subList(start, end), pageable, listContentSearchResponseDTOS.size());

    } // findByTitleOrContent(String query, Pageable pageable)


    /**
     * TAG로 게시물 검색
     * @param tagContent - 이용자가 검색 요청한 제목 혹은 내용 일부분 검색어
     * @return Page<ManualListTagContentSearchResponseDTO> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    public Page<ManualListTagContentSearchResponseDTO> findByTag(String tagContent, Pageable pageable) {

        log.info("ManualQuerydslRepository의 findByTag(String tagContent, Pageable pageable)가 호출 되었습니다!");

        List<ManualListTagContentSearchResponseDTO> listTagContentSearchResponseDTOS = jpaQueryFactory
                .select(Projections.constructor(ManualListTagContentSearchResponseDTO.class,
                        manual.manualNo,
                        manual.title,
                        member.nickname,
                        manual.createAt,
                        manual.updateAt,
                        manual.content,
                        manualTag.tagContent))

                .from(manual)
                .innerJoin(manual.writer, member)
                .join(manualTag)
                .on(manualTag.manual.manualNo.eq(manual.manualNo))
                .where(manualTag.tagContent.containsIgnoreCase(tagContent))
                .orderBy(manual.manualNo.desc())
                .fetch();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), listTagContentSearchResponseDTOS.size());

        return new PageImpl<>(listTagContentSearchResponseDTOS.subList(start, end), pageable, listTagContentSearchResponseDTOS.size());

    } // findByTag(String tagContent, Pageable pageable) 끝
} // class 끝
