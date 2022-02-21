package org.comunity.hongga.model.entity.manual;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.entity.base.BaseDateTime;
import org.comunity.hongga.model.entity.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 회원 DB 관련
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.14 최초 작성
 *    주니하랑, 1.1.0, 2022.02.16 TAG 관련 추가
 *    주니하랑, 1.1.1, 2022.02.20 게시글 제목, 내용 수정 관련 Method 구현
 *    주니하랑, 1.0.2, 2022.02.21 게시글 제목, 내용, Tag Validation Annotation 수정 및 추가
 * </pre>
 *
 * @author 주니하랑
 * @version 주니하랑, 1.0.2, 2022.02.21 게시글 제목, 내용, Tag Validation Annotation 수정 및 추가
 * @See ""
 * @see <a href=""></a>
 */

@NoArgsConstructor @AllArgsConstructor @Getter @ToString @Slf4j
@Entity public class Manual extends BaseDateTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manualNo;

    @ManyToOne(targetEntity = Member.class) @JoinColumn(name = "member_no") private Member writer;

    @Column(length = 100, nullable = false) @NotBlank private String title;

    // TODO - 글, 사진 (Editor 사용)
    @Lob @Column(length = 65535, nullable = false) @NotEmpty private String content;

    @Builder public Manual(Member writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    } // 생성자 끝

    public void changeTitle(String title) {
        log.info("Manual Entity가 호출 되었습니다!");
        log.info("changeTitle(String title)이 호출 되었습니다!");

        log.info("이용자가 수정한 게시글 제목이 DB에 저장 된 내용과 다른지 검사 하겠습니다!");
        if (!this.title.equals(title)) {

            log.info("이용자가 수정한 게시글 제목이 DB에 저장 된 내용과 달라 Entity에 내용을 수정하겠습니다!");

            this.title = title;
        } // if (this.title.equals(title)) 끝

    } // changeTitle(String title) 끝
    public void changeContent(String content) {

        log.info("Manual Entity가 호출 되었습니다!");
        log.info("changeContent(String content)");

        log.info("이용자가 수정한 게시글 내용이 DB에 저장 된 내용과 다른지 검사 하겠습니다!");

        if (!this.content.equals(content)) {

            log.info("이용자가 수정한 게시글 내용이 DB에 저장 된 내용과 달라 Entity에 내용을 수정하겠습니다!");

            this.content = content;
        } // if (!this.content.equals(content)) 끝
    } // changeContent(String content) 끝

} // class 끝
