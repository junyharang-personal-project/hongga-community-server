package org.comunity.hongga.repository.manual;

import org.comunity.hongga.model.entity.manual.ManualImage;
import org.comunity.hongga.model.entity.manual.ManualTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Manual Image Repository
 * <pre>
 * <b>History:/b>
 *    주니하랑, 1.0.0, 2022.02.23 최초 작성
 *    주니하랑, 1.0.1, 2022.02.25 삭제 Query 작성 및 구현
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.25 삭제 Query 작성 및 구현
 * @See ""
 * @see <a href=""></a>
 */

public interface ManualImageRepository extends JpaRepository<ManualImage, Long> {

    @Query(value = "select mi " +
            "from ManualImage as mi " +
            "left join mi.manual ma " +
            "where ma.manualNo =:manualNo ")
    List<ManualImage> findByManualNo(@Param("manualNo") Long manualNo);

//    @Query(value = "select mi from ManualImage mi join fetch mi.manual m where mi.manual.manualNo =:manualNo")
//    Optional<ManualImage> findByManualNo(@Param("manualNo") Long manualNo);

    /**
     * 사진 삭제
     * @param manualNo - 삭제 대상 게시글 고유 번호 해당 고유 번호와 매칭된 사진 삭제 처리
     * @see ""
     */

    @Modifying @Transactional
    @Query(value = "delete from ManualImage where manual.manualNo =:manualNo")
    void deleteByManualNo (@Param("manualNo") Long manualNo);

} // interface 끝
