package org.comunity.hongga.model.dto.request.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @Slf4j
public class ManaualTagDTO {

    @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
    private String tagContent;

} // class 끝
