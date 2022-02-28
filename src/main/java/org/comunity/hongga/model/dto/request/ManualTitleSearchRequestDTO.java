package org.comunity.hongga.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

/**
 * 사용 설명서 검색 관련 요청 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.28 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.28 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @Slf4j @ToString
public class ManualTitleSearchRequestDTO {

    @NotBlank private String title;

//    public ManualTitleSearchRequestDTO searchTitle(String title) {
//
//        log.info("ManualSearchRequestDTO의 searchTitle(String title)이 호출 되었습니다!");
//
//        this.title = title;
//
//        return this;
//    } // searchTitle(String title) 끝
//
//    public ManualTitleSearchRequestDTO searchContent(String content) {
//
//        log.info("ManualSearchRequestDTO의 searchContent(String content)이 호출 되었습니다!");
//
//        this.content = content;
//
//        return this;
//
//    } // searchContent(String content) 끝
//
//    public ManualTitleSearchRequestDTO searchTitleContent(String title, String content) {
//
//        log.info("ManualSearchRequestDTO의 searchTitleContent(String title, String content)이 호출 되었습니다!");
//
//        this.title = title;
//        this.content = content;
//
//        return this;
//
//    } // searchTitleConent(String title, String content) 끝
//
//    public ManualTitleSearchRequestDTO searchTag(String tagContent) {
//
//        log.info("ManualSearchRequestDTO의 searchTag(String tagContent)이 호출 되었습니다!");
//
//        this.tagContent = tagContent;
//
//        return this;
//
//    } // searchTag(String tagContent) 끝

} // class 끝
