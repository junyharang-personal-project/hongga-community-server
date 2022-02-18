package org.comunity.hongga.repository.manual;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.response.manual.ManualListResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Manual Repository
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.14 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.14 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

public interface ManualRepository extends JpaRepository<Manual, Long> {

    @Query(value = "select m.nickname, ma.title, ma.registerDate, ma.modifyDate from Manual ma inner join Member m on ma.writer.memberNo = m.memberNo")
    Page<ManualListResponseDTO> findAllWithFetchJoin(Pageable pageable);


//    @Query(value = "select d from DiscussionBoard d join fetch d.user",
//            countQuery = "select count(d) from DiscussionBoard d")
//    Page<DiscussionBoard> dfindAllWithFetchJoin(Pageable pageable);
} // interface 끝
