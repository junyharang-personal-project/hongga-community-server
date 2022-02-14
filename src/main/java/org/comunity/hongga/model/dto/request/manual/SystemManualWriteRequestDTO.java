package org.comunity.hongga.model.dto.request.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.hongga.model.entity.manual.SystemManual;
import org.comunity.hongga.model.entity.member.Member;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

/**
 * 회원 DB 관련
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.14 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.14 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Getter @NoArgsConstructor @AllArgsConstructor
public class SystemManualWriteRequestDTO {

    @NotEmpty private String title;             // 메뉴얼 게시글 제목
    @NotEmpty private String content;           // 메뉴얼 게시글 내용(글/사진)

    // TODO - HashTAG 추가

    @Builder public SystemManual toEntity(SystemManualWriteRequestDTO writeRequestDTO, Optional<Member> member) {

        return SystemManual.builder()
                .writer(member.get())
                .title(writeRequestDTO.title)
                .content(writeRequestDTO.content)
                .build();

    } // toEntity(SystemManualWriteRequestDTO writeRequestDTO, Optional<Member> member) 끝


} // class 끝
