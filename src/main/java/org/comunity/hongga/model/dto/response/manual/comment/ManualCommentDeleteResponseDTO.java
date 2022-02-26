package org.comunity.hongga.model.dto.response.manual.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용 설명서 댓글 삭제를 위한 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.26 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.26 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ManualCommentDeleteResponseDTO {

    private Long manualCommentNo;   // 댓글 고유 번호
    private String nickname;        // 작성자 별명
    private String commentContent;  // 댓글 내용
    private LocalDateTime creatAt;  // 댓글 작성일
    private LocalDateTime updateAt; // 댓글 수정일


} // class 끝
