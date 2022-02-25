package org.comunity.hongga.model.dto.response.manual;

import lombok.*;
import org.comunity.hongga.model.dto.request.manual.ManualImageDTO;
import org.comunity.hongga.model.dto.request.manual.ManualTagDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.ManualImage;
import org.comunity.hongga.model.entity.manual.ManualTag;
import org.comunity.hongga.model.entity.member.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 사용 설명서 관련 상세 조회 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.15 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Data @NoArgsConstructor @Builder
public class ManualDetailResponseDTO {

    private Long manualNo;
    private String title;
    private String nickname;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String content;
    private String imageUuid;
    private String imagePath;
    private String imgName;
    private String tagContent;



    public ManualDetailResponseDTO(Long manualNo, String title, String nickname, LocalDateTime createAt, LocalDateTime updateAt, String content, String imageUuid, String imagePath, String imgName, String tagContent) {

        this.manualNo = manualNo;
        this.title = title;
        this.nickname = nickname;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.content = content;
        this.imageUuid = imageUuid;
        this.imagePath = imagePath;
        this.imgName = imgName;
        this.tagContent = tagContent;

    } // 생성자 끝
} // class 끝
