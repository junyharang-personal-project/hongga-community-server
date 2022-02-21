package org.comunity.hongga.model.dto.request.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * 메뉴얼 Tag 관련 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.21 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.21 최초 작성
 * @See ""
 * @see <a href="코드로 배우는 스프링 부트 웹 프로젝트 P.419"></a>
 */


@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ManualTagDTO {

    @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
    private String tagContent;

} // class 끝
