package org.comunity.hongga.model.dto.request.manual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Lob;

@Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class ManualUpdateRequestDTO {

    @Column(length = 100, nullable = false) private String title;

    // TODO - 글, 사진 (Editor 사용)
    @Lob
    @Column(length = 65535, nullable = false) private String content;

} // class 끝
