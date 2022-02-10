package org.comunity.hongga.model.entity.member;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter @Setter @NoArgsConstructor @Builder @AllArgsConstructor
@Entity @Table(name = "authority") public class Authority {

    @Id @Column(name = "authority_name", length = 50)
    private String authorityName;
} // class 끝
