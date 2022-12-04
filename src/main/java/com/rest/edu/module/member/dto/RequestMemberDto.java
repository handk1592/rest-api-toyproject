package com.rest.edu.module.member.dto;

import com.rest.edu.global.valid.ValidationGroups;
import com.rest.edu.module.member.entity.Member;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RequestMemberDto {
    @NotEmpty(message="parameter value is empty", groups = ValidationGroups.NotEmptyGroup.class)
    private String name;

    @Positive(message="양수만 가능", groups = ValidationGroups.NumericCheckGroup.class)
    @Max(value = 999, message = "3자리 제한", groups = ValidationGroups.SizeCheckGroup.class)
    private Long age;

    @Pattern(
            message = "휴대폰 형식 맞지 않음",
            regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
            groups = ValidationGroups.PatternCheckGroup.class
    )
    private String hp;

    @Email(message = "email 형식 맞지 않음", groups = ValidationGroups.PatternCheckGroup.class)
    private String email;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .age(age)
                .hp(hp)
                .email(email)
                .build();
    }

    @Builder
    public RequestMemberDto(String name, Long age, String hp, String email) {
        this.name = name;
        this.age = age;
        this.hp = hp;
        this.email = email;
    }
}
