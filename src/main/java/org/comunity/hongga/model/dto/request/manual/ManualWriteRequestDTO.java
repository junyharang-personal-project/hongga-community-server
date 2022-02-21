package org.comunity.hongga.model.dto.request.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.member.Member;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 회원 DB 관련
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.14 최초 작성
 *    주니하랑, 1.0.1, 2022.02.16 TAG 추가
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.16 TAG 추가
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor
public class ManualWriteRequestDTO {

    private Long manualNo;

    @NotEmpty private String title;             // 메뉴얼 게시글 제목
    @NotEmpty private String content;           // 메뉴얼 게시글 내용(글/사진)

//    private String tagContent0;
//    private String tagContent1;
//    private String tagContent2;
//    private String tagContent3;
//    private String tagContent4;
//    private String tagContent5;
//    private String tagContent6;
//    private String tagContent7;
//    private String tagContent8;
//    private String tagContent9;

//    private String tagContent;                  // TAG

    // TODO - HashTAG 추가

    private List<ManualImageDTO> imageDTOLIST = new ArrayList<>();

    @Column(length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있으며, 최대 10개 까지 등록 가능 합니다!")
    private List<ManualTagDTO> tagDTOLIST = new ArrayList<>(10);

    @Builder public Manual toEntity(ManualWriteRequestDTO writeRequestDTO, Optional<Member> writer) {

        return Manual.builder()
                .writer(writer.get())
                .title(writeRequestDTO.title)
                .content(writeRequestDTO.content)
                .build();

    } // toEntity(SystemManualWriteRequestDTO writeRequestDTO, Optional<Member> member) 끝
} // class 끝
