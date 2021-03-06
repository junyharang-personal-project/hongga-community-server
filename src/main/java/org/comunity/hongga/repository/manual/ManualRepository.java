package org.comunity.hongga.repository.manual;

import org.comunity.hongga.model.entity.manual.Manual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = "select ma " +
            "from Manual ma join fetch ma.writer " +
            "where ma.manualNo =:manualNo ")
    Optional<Manual> findByManualNo(@Param("manualNo") Long manualNo);

} // interface 끝
