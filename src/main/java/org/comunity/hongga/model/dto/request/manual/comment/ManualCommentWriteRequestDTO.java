package org.comunity.hongga.model.dto.request.manual.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.comment.ManualComment;
import org.comunity.hongga.model.entity.member.Member;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

/**
 * 메뉴얼 댓글 작성 기능답 요청 관련 DTO
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

@Getter @NoArgsConstructor @Slf4j
public class ManualCommentWriteRequestDTO {

    @NotBlank private String commentContent;

    public ManualComment toEntity(Optional<Manual> manual, Optional<Member> writer, ManualCommentWriteRequestDTO writeRequestDTO) {

        log.info("ManualCommentWriteRequestDTO의 toEntity(Optional<Manual> manual, Optional<Member> writer, ManualWriteRequestDTO writeRequestDTO)가 호출 되었습니다!");

        log.info("요청 이용자가 작성한 댓글(DTO)을 Entity로 변환하겠습니다! ");

        return ManualComment.builder()
                .manual(manual.get())
                .writer(writer.get())
                .commentContent(writeRequestDTO.getCommentContent())
                .build();

    } // toEntity(Optional<Manual> manual, Optional<Member> writer, ManualWriteRequestDTO writeRequestDTO) 끝

} // class 끝
