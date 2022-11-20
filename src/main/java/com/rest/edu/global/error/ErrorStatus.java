package com.rest.edu.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorStatus {

      INVALID_PARAMETER("S01", "invalid parameter", HttpStatus.BAD_REQUEST)
    , NON_EXIST_MEMBER("S02", "non exist member", HttpStatus.BAD_REQUEST)

    , INVALID_API_KEY("S11", "Authentication failed from Invalid API Key", HttpStatus.UNAUTHORIZED)

    , INTERNAL_SERVER_ERROR("S99", "internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    ErrorStatus(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

}
