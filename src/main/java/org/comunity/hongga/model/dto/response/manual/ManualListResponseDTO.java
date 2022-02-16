package org.comunity.hongga.model.dto.response.manual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.comunity.hongga.model.entity.base.BaseDateTime;
import org.comunity.hongga.model.entity.manual.Manual;

import java.time.LocalDateTime;

/**
 * DB 조회 값을 Java로 변환하기 위한 Class
 * <pre>
 * <b>History:/b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.16 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class ManualListResponseDTO {

    private Long manualNo;              // 메뉴얼 Index 번호
    private String nickname;
    private String title;               // 제목
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
//    private Long readHit;               // 조회수

    /*
    Entity에서 BaseDateTime을 상속함으로 별도 처리 필요 없음
     */

    // TODO - 조회수 처리

} // class 끝
