package org.comunity.hongga.model.entity.manual;

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

@Entity public class Tag {

    @Id @Column(name = "tag_content", length = 10) @Size(message = "Tag는 10자리 이하만 등록할 수 있습니다!")
    private Long tagContent;



} // class 끝
