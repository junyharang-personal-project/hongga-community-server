package org.comunity.hongga.repository.manual;

import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Manual Repository
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.14 최초 작성
 *    주니하랑, 1.2.0, 2022.02.18 목록 조회, 상세 조회 동적 Query용 Query dsl 대신 JPQL로 변경
 * </pre>
 *
 * @author 주니하랑
 * @version 1.2.0, 2022.02.18 목록 조회, 상세 조회 동적 Query용 Query dsl 대신 JPQL로 변경
 * @See ""
 * @see <a href=""></a>
 */

public interface ManualRepository extends JpaRepository<Manual, Long> {

    @Query(value = "select ma.manualNo, w.nickname, ma.title, ma.registerDate, ma.modifyDate " +
                    "from Manual ma " +
                    "left join ma.writer w ")
    Page<Manual> findAllWithFetchJoin(Pageable pageable);

    // TODO - 상세 조회 시 회원 정보가 모두 나오지 않게 하고, 닉네임만 나오게 처리 필요

    @Query(value = "select ma " +
            "from Manual ma " +
            "left join ma.writer w " +
            "where ma.manualNo =:manualNo ")
    Optional<Manual> findByManualAndWriter(@Param("manualNo") Long manualNo);

} // interface 끝
