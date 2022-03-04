package org.comunity.hongga.model.dto.request.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.member.Member;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
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
 *    주니하랑, 1.0.2, 2022.02.21 게시글 제목, 내용, Tag Validation Annotation 수정 및 추가
 *    주니하랑, 1.0.3, 2022.02.25 글 등록 시 작성자가 DB에 들어가지 않는 문제로 N개의 Image 처리, Tag 처리 잠시 포기
 *    주니하랑, 1.0.4, 2022.02.27 글 등록 시 작성자 DB에 안 들어가는 문제 해결 N개의 Image 처리, Tag처리 완료
 *    주니하랑, 1.1.0, 2022.03.04 글 등록, 수정 RequestDTO 결합으로 인한 수정
 * </pre>
 *
 * @author 주니하랑
 * @version 1.1.0, 2022.03.04 글 등록, 수정 RequestDTO 결합으로 인한 수정
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor @Slf4j
public class ManualWriteAndUpdateRequestDTO {

    private Long manualNo;

    @NotBlank private String title;             // 메뉴얼 게시글 제목

    private String writer;

    @Lob @Column(length = 65535, nullable = false) @NotEmpty private String content;   // 메뉴얼 게시글 내용(글/사진)

    private String uuid;
    private String imgName;
    private String path;

    @Column(length = 30) @Size(max = 10, message = "Tag는 10자리 이하만 등록할 수 있습니다!") private String tagContent;

    private List<ManualImageDTO> imageDTOLIST = new ArrayList<>();

    @Column(length = 30) @Size(max = 10, message = "Tag는 10자리 이하만 등록할 수 있습니다!")
    private List<ManualTagDTO> tagDTOLIST = new ArrayList<>(10);

    @Builder public Manual toEntity(ManualWriteAndUpdateRequestDTO writeRequestDTO, Optional<Member> member) {

        log.info("ManualWriteRequestDTO의 toEntity(ManualWriteRequestDTO writeRequestDTO, Optional<Member> member)가 호출 되었습니다!");

        return Manual.builder()
                .title(writeRequestDTO.title)
                .writer(member.get())
                .content(writeRequestDTO.content)
                .build();

    } // toEntity(ManualWriteRequestDTO writeRequestDTO, Optional<Member> member)
} // class 끝
