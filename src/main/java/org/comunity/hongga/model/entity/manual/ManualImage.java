package org.comunity.hongga.model.entity.manual;

import lombok.*;

import javax.persistence.*;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor @ToString(exclude = "manual")
@Entity public class ManualImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageNo;

    private String uuid;
    private String imgName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "manual_no")
    private Manual manual;

} // class 끝
