package org.comunity.hongga.model.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public enum MemberGrade {

    GUEST("손님"),
    FAMILY("가족"),
    ADMIN("관리자");

    private String description;

} // enum class 끝
