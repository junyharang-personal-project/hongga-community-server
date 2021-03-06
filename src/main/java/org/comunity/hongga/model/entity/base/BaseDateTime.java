package org.comunity.hongga.model.entity.base;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 작성일과 수정일을 자동 입력
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.08 최초 작성
 * @See "코드로 배우는 스프링 부트 웹 프로젝트"
 * @see <a href=""></a>
 */

@EntityListeners(value = {
        AuditingEntityListener.class
})

@MappedSuperclass @Getter
public class BaseDateTime {

    // 작성, 등록 일시
    @CreatedDate @Column(name = "create_at", updatable = false)
    private LocalDateTime createAt;

    // 수정, 변경 일시
    @LastModifiedDate @Column(name = "update_at")
    private LocalDateTime updateAt;

} // class 끝
