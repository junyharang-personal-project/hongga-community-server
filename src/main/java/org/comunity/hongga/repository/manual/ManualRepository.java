package org.comunity.hongga.repository.manual;

import org.comunity.hongga.model.dto.request.manual.ManualUpdateRequestDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
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

    @Query(value = "select ma.manualNo, ma.title, ma.registerDate, ma.modifyDate, ma.content \f" +
            "from Manual ma " +
            "where ma.manualNo =:manualNo ")
    Optional<Manual> findByManualNo(Long manualNo);

    @Transactional @Modifying
    @Query("update Manual ma set ma.title =:manualUpdateRequestDTO, ma.content =:manualUpdateRequestDTO where ma.manualNo =:manualNo ")
    void updateManual(@Param("manualUpdateRequestDTO") ManualUpdateRequestDTO manualUpdateRequestDTO, @Param("manualNo") Long manualNo);


} // interface 끝
