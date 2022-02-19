package org.comunity.hongga.model.entity.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.manual.Manual;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * 사용 설명서 TAG DB 관련
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.16 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

//@NoArgsConstructor @AllArgsConstructor
//@Entity public class ManualTag {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long tagNo;
//
//    @ManyToOne(targetEntity = Manual.class, fetch = FetchType.LAZY) @JoinColumn(name = "manual_no")
//    private Manual manual;
//
//    @Column(name = "tag_content", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
//    private String tagContent;
//
//    @Column(name = "tag_content1", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
//    private String tagContent1;
//
//    @Column(name = "tag_content2", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
//    private String tagContent2;
//
//    @Column(name = "tag_content3", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
//    private String tagContent3;
//
//    @Column(name = "tag_content4", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
//    private String tagContent4;
//
//    @Column(name = "tag_content5", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
//    private String tagContent5;
//
//    @Column(name = "tag_content6", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
//    private String tagContent6;
//
//    @Column(name = "tag_content7", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
//    private String tagContent7;
//
//    @Column(name = "tag_content8", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
//    private String tagContent8;
//
//    @Column(name = "tag_content9", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
//    private String tagContent9;
//
//
//    @Builder public ManualTag(Manual manual, String tagContent, String tagContent1, String tagContent2, String tagContent3, String tagContent4, String tagContent5, String tagContent6, String tagContent7, String tagContent8, String tagContent9) {
//        this.manual = manual;
//        this.tagContent = tagContent;
//        this.tagContent1 = tagContent1;
//        this.tagContent2 = tagContent2;
//        this.tagContent3 = tagContent3;
//        this.tagContent4 = tagContent4;
//        this.tagContent5 = tagContent5;
//        this.tagContent6 = tagContent6;
//        this.tagContent7 = tagContent7;
//        this.tagContent8 = tagContent8;
//        this.tagContent9 = tagContent9;
//    } // 생성자 끝
//
//} // class 끝
