package com.rest.edu.global.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 값 존재 할때 응답결과 포함
public class ExceptionResponse {
    @JsonProperty("code")
    private String errorCode;

    @JsonProperty("message")
    private String errorMessage;

    @JsonProperty("description")
    private String errorDescription;
}