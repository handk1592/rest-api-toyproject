package com.rest.edu.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private String code;
    private String message;
}
