package org.comunity.hongga.model.entity.manual.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.dto.request.manual.comment.ManualCommentUpdateRequestDTO;
import org.comunity.hongga.model.entity.base.BaseDateTime;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.member.Member;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

/**
 * 메뉴얼 댓글 Entity
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


@Getter @NoArgsConstructor @EntityListeners(AuditingEntityListener.class) @Slf4j @Table(name = "tbl_manual_comment")
@Entity public class ManualComment extends BaseDateTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Manual.class) @JoinColumn(name = "manual_no")
    private Manual manual;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member.class) @JoinColumn(name = "memberNo")
    private Member writer;

    private String commentContent;

    @Builder public ManualComment(Manual manual, Member writer, String commentContent) {

        this.manual = manual;
        this.writer = writer;
        this.commentContent = commentContent;

    } // 생성자 끝

    public ManualComment updateComment (ManualCommentUpdateRequestDTO updateRequestDTO) {

        log.info("ManualReply의 updateReply (ManualReplyUpdateRequestDTO updateRequestDTO)이 호출 되었습니다!");

        if (!Objects.equals(this.commentContent, updateRequestDTO.getCommentContent())) {

            log.info("이용자가 댓글 수정을 요청했으며, DB에 저장되어 있는 댓글 내용과 달라 변경 하도록 하겠습니다!");

            this.commentContent = updateRequestDTO.getCommentContent();

            log.info("수정이 정상적으로 완료 되었습니다!");

        } // updateReply (ManualReplyUpdateRequestDTO updateRequestDTO)

        return this;

    } // updateReply (ManualReplyUpdateRequestDTO updateRequestDTO) 끝

} // class 끝
