package org.comunity.hongga.constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * 목록 조회를 위한 페이징 처리 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.09 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.09 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Pagination {

    // 전체 페이지 수
    private Integer totalPages;

    // 전체 데이터 수
    private Long totalElements;

    // 현재 페이지
    private Integer currentPage;

    // 현재 페이지에 나올 데이터 수
    private Integer currentElements;

    public Pagination(Page<?> page){

        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.currentPage = page.getNumber();
        this.currentElements = page.getNumberOfElements();

    } // 생성자 끝
} // class 끝
