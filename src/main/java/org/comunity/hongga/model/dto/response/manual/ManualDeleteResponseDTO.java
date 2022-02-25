package org.comunity.hongga.model.dto.response.manual;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.member.Member;

import java.time.LocalDateTime;

/**
 * 사용 설명서 관련 삭제를 위한 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.25 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.25 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Data @NoArgsConstructor @Builder
public class ManualDeleteResponseDTO {

    private Long manualNo;
    private String title;
    private Member writer;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String content;
    private String imageUuid;
    private String imagePath;
    private String imgName;
    private String tagContent;



    public ManualDeleteResponseDTO(Long manualNo, String title, Member member, LocalDateTime createAt, LocalDateTime updateAt, String content, String imageUuid, String imagePath, String imgName, String tagContent) {

        this.manualNo = manualNo;
        this.title = title;
        this.writer = member;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.content = content;
        this.imageUuid = imageUuid;
        this.imagePath = imagePath;
        this.imgName = imgName;
        this.tagContent = tagContent;

    } // 생성자 끝
} // class 끝
