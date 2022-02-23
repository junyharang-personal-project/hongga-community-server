package org.comunity.hongga.repository.manual;

import com.querydsl.core.types.Projections;
import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListSearchResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Manual Repository
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.14 최초 작성
 *    주니하랑, 1.2.0, 2022.02.18 목록 조회, 상세 조회 동적 Query용 Query dsl 대신 JPQL로 변경
 *    주니하랑, 1.2.1, 2022.02.19 수정 관련 JPQL 구현
 *    주니하랑, 1.2.2, 2022.02.22 목록 조회 Query dsl 처리로 인한 Query 삭제
 * </pre>
 *
 * @author 주니하랑
 * @version 1.2.2, 2022.02.22 목록 조회 Query dsl 처리로 인한 Query 삭제
 * @See ""
 * @see <a href=""></a>
 */

public interface ManualRepository extends JpaRepository<Manual, Long> {

    /**
     * 상세 조회 Method
     * @param manualNo 특정 게시글 조회를 위한 게시글 고유 번호
     * @return Optional<Manual> 조회 된 게시글과 작성자 반환
     */

    // TODO - 상세 조회 시 회원 정보가 모두 나오지 않게 하고, 닉네임만 나오게 처리 필요

    @Query(value = "select ma.manualNo, ma.title, w.nickname, ma.createAt ,ma.updateAt, ma.content, mi, mt " +
            "from Manual ma " +
            "inner join ma.writer, Member w " +
            "left outer join ManualTag mt on mt.manual = ma " +
            "left outer join ManualImage mi on mi.manual = ma " +
            "where ma.manualNo =:manualNo")
    List<Object[]> findByManualDetail(@Param("manualNo") Long manualNo);

    /**
     * 게시글 수정 전 해당 게시글 조회를 위한 Method
     * @param manualNo 특정 게시글 조회를 위한 게시글 고유 번호
     * @return Optional<Manual> 조회 된 게시글 반환
     */

    @Query(value = "select ma " +
            "from Manual ma " +
            "where ma.manualNo =:manualNo ")
    Optional<Manual> findByManualNo(@Param("manualNo") Long manualNo);


    /**
     * 게시글 삭제 전 해당 게시글 조회를 위한 Method
     * @param manualNo 해당 게시글 삭제를 위한 게시글 고유 번호
     * @param memberNo 해당 게시글 작성자 고유 번호
     * @return Optional<Manual> 수정 된 게시글 반환
     */

    @Query(value = "select ma " +
                   "from Manual ma " +
                   "left join ma.writer w " +
                   "where ma.manualNo =:manualNo " +
                   "and ma.writer.memberNo =:memberNo ")
    Optional<Manual> findByManualAndWriter(@Param("manualNo")Long manualNo, @Param("memberNo") Long memberNo);



//    List<Object[]> getManualWithAll(@Param("manualNo") Long manualNo);
} // interface 끝
