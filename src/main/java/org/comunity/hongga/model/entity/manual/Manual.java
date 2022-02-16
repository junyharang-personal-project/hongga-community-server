package org.comunity.hongga.model.entity.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.base.BaseDateTime;
import org.comunity.hongga.model.entity.member.Member;

import javax.persistence.*;

/**
 * 회원 DB 관련
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

@NoArgsConstructor @AllArgsConstructor
@Entity public class Manual extends BaseDateTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manualNo;

    @ManyToOne @JoinColumn(name = "member_no") private Member writer;

    @Column(length = 100, nullable = false) private String title;

    // TODO - 글, 사진 (Editor 사용)
    @Lob @Column(length = 65535) private String content;

    // TODO - TAG, 댓글, 대댓글 처리

    @Builder public Manual(Member writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    } // 생성자 끝

} // class 끝
