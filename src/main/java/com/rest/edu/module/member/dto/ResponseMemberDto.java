package com.rest.edu.module.member.dto;

import com.rest.edu.global.response.ResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class ResponseMemberDto extends ResponseDto {
    private String name;
    private String age;
    private String gender;
}
