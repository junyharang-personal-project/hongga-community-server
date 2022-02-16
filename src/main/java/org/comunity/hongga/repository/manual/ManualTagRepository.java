package org.comunity.hongga.repository.manual;

import org.comunity.hongga.model.entity.manual.ManualTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ManualTagRepository extends JpaRepository<ManualTag, Long> {

    @Query(value = "select t from ManualTag t join fetch t.manual where t.manual.manualNo =:manualNo")
    Optional<ManualTag> findByManualNo(@Param("manualNo") Long manualNo);

} // interface 끝
