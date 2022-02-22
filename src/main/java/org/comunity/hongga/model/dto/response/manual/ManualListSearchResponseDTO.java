package org.comunity.hongga.model.dto.response.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.base.BaseDateTime;
import org.comunity.hongga.model.entity.member.Member;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class ManualListSearchResponseDTO {

    private Long manualNo;

    private String nickname;

    private String title;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    //TODO - 좋아요 수

} // class 끝
