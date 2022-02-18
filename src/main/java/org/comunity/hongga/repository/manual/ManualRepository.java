package org.comunity.hongga.repository.manual;

import org.comunity.hongga.model.entity.manual.Manual;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

/**
 * Manual Repository
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.14 최초 작성
 *    주니하랑, 1.1.0, 2022.02.18 목록 조회 동적 Query용 Query dsl 대신 JPQL로 변경
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.0, 2022.02.18 목록 조회 동적 Query용 Query dsl 대신 JPQL로 변경
 * @See ""
 * @see <a href=""></a>
 */

public interface ManualRepository extends JpaRepository<Manual, Long> {

    @Query(value = "select ma.manualNo, w.nickname, ma.title, ma.registerDate, ma.modifyDate " +
                    "from Manual ma " +
                    "left join ma.writer w ")
    Page<Manual> findAllWithFetchJoin(Pageable pageable);


//    @Query(value = "select d from DiscussionBoard d join fetch d.user",
//            countQuery = "select count(d) from DiscussionBoard d")
//    Page<DiscussionBoard> dfindAllWithFetchJoin(Pageable pageable);
} // interface 끝
