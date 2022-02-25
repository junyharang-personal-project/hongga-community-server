package org.comunity.hongga.model.dto.request.manual.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 메뉴얼 댓글 수정 기능 관련 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.26 최초 작성
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.26 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor
public class ManualCommentUpdateRequestDTO {

    @NotBlank private String commentContent;

    // 댓글 수정일 변경
    private LocalDateTime updateAt = LocalDateTime.now();

} // class 끝
