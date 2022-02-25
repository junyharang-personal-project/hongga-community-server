package org.comunity.hongga.repository.manual;

import org.comunity.hongga.model.entity.manual.ManualImage;
import org.comunity.hongga.model.entity.manual.ManualTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ManualImageRepository extends JpaRepository<ManualImage, Long> {

    @Query(value = "select mi from ManualImage mi join fetch mi.manual m where mi.manual.manualNo =:manualNo")
    Optional<ManualImage> findByManualNo(@Param("manualNo") Long manualNo);

    @Modifying @Transactional
    @Query(value = "delete from ManualImage where manual.manualNo =:manualNo")
    void deleteByManualNo (@Param("manualNo") Long manualNo);

} // interface 끝
