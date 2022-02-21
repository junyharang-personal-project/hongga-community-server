package org.comunity.hongga.model.entity.manual;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
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

@Getter @Builder @NoArgsConstructor @AllArgsConstructor @Slf4j @ToString(exclude = "manual")
@Entity public class ManualTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagNo;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "manual_no")
    private Manual manual;

    @Column(length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
    private String tagContent;

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
//    @Builder public ManualTag(Manual manual, String tagContent) {
//        this.manual = manual;
//        this.tagContent = tagContent;
//    } // 생성자 끝

//    public void changeTag(String tagContent0, String tagContent1, String tagContent2, String tagContent3, String tagContent4, String tagContent5, String tagContent6, String tagContent7, String tagContent8, String tagContent9) {
//
//        log.info("ManualTag가 호출 되었습니다!");
//        log.info("changeTag(ManualTag manualTag)가 동작 하였습니다!");
//
//        log.info("이용자 수정 요청 중, Tag 내용이 DB에 저장된 내용과 달라 수정 요청이 왔는지 검증 하겠습니다!");
//
//            if (!(this.tagContent0).equals(tagContent0)) {
//                log.info("Tag 0번이 수정 되어 Entity에 값을 변경 하겠습니다!");
//                this.tagContent0 = tagContent0;
//            } else if (!this.tagContent1.equals(tagContent1)) {
//                log.info("Tag 1번이 수정 되어 Entity에 값을 변경 하겠습니다!");
//                this.tagContent1 = tagContent1;
//            } else if (!this.tagContent2.equals(tagContent2)) {
//                log.info("Tag 2번이 수정 되어 Entity에 값을 변경 하겠습니다!");
//                this.tagContent2 = tagContent2;
//            } else if (!this.tagContent3.equals(tagContent3)) {
//                log.info("Tag 3번이 수정 되어 Entity에 값을 변경 하겠습니다!");
//                this.tagContent3 = tagContent3;
//            } else if (!this.tagContent4.equals(tagContent4)) {
//                log.info("Tag 4번이 수정 되어 Entity에 값을 변경 하겠습니다!");
//                this.tagContent4 = tagContent4;
//            } else if (!this.tagContent5.equals(tagContent5)) {
//                log.info("Tag 5번이 수정 되어 Entity에 값을 변경 하겠습니다!");
//                this.tagContent5 = tagContent5;
//            } else if (!this.tagContent6.equals(tagContent6)) {
//                log.info("Tag 6번이 수정 되어 Entity에 값을 변경 하겠습니다!");
//                this.tagContent6 = tagContent6;
//            } else if (!this.tagContent7.equals(tagContent7)) {
//                log.info("Tag 7번이 수정 되어 Entity에 값을 변경 하겠습니다!");
//                this.tagContent7 = tagContent7;
//            } else if (!this.tagContent8.equals(tagContent8)) {
//                log.info("Tag 8번이 수정 되어 Entity에 값을 변경 하겠습니다!");
//                this.tagContent8 = tagContent8;
//            } else if (!this.tagContent9.equals(tagContent9)) {
//                log.info("Tag 9번이 수정 되어 Entity에 값을 변경 하겠습니다!");
//                this.tagContent9 = tagContent9;
//            } // changeTag if문 끝
//    } // changeTag(String tagContent0, String tagContent1, String tagContent2, String tagContent3, String tagContent4, String tagContent5, String tagContent6, String tagContent7, String tagContent8, String tagContent9) 끝
} // class 끝
