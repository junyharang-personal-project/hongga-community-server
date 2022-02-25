package org.comunity.hongga.repository.manual;

//import org.comunity.hongga.model.entity.manual.ManualTag;
import org.comunity.hongga.model.entity.manual.ManualTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Manual TAG Repository
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

public interface ManualTagRepository extends JpaRepository<ManualTag, Long> {

    @Query(value = "select mt from ManualTag mt join fetch mt.manual where mt.manual.manualNo =:manualNo")
    Optional<ManualTag> findByManualNo(@Param("manualNo") Long manualNo);

    @Modifying @Transactional
    @Query(value = "delete from ManualTag where manual.manualNo =:manualNo")
    void deleteByManualNo (@Param("manualNo") Long manualNo);
} // interface 끝
