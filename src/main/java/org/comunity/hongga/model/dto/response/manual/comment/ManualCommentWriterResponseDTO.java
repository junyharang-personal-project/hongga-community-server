package org.comunity.hongga.model.dto.response.manual.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 메뉴얼 댓글 작성 기능 응청 관련 DTO
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

@Getter @NoArgsConstructor @AllArgsConstructor
public class ManualCommentWriterResponseDTO {

    private Long no;                // 댓글 고유 번호
    private String nickname;        // 작성자 별명
    private String commentContent;  // 댓글 내용
    private LocalDateTime creatAt;  // 댓글 작성일
    private LocalDateTime updateAt; // 댓글 수정일

} // class 끝
