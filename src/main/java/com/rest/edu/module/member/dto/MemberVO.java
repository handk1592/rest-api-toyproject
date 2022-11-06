package com.rest.edu.module.member.dto;

import com.rest.edu.global.valid.ValidationGroups;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class MemberVO {
    @NotEmpty(message="parameter value is empty", groups = ValidationGroups.NotEmptyGroup.class)
    private String name;

    @NotEmpty(message="parameter value is empty", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp="\\d{1,3}", message="parameter not number format ", groups = ValidationGroups.PatternCheckGroup.class)
    private String age;
}
