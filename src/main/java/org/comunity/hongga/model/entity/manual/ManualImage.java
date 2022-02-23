package org.comunity.hongga.model.entity.manual;

import lombok.*;

import javax.persistence.*;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor @ToString(exclude = "manual")
@Entity @Table(name = "tbl_manual_image") public class ManualImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageNo;

    private String uuid;
    private String imgName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manual manual;

} // class 끝
