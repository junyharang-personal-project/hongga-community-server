package org.comunity.hongga.model.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter @NoArgsConstructor @AllArgsConstructor
public class MemberSignInRequestDTO {

    @NotEmpty private String email;
    @NotEmpty private String password;

} // class 끝
