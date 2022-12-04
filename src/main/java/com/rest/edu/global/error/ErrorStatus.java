package com.rest.edu.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorStatus {

      INVALID_FORMAT        ("ERR001",  "request body is missing ",      HttpStatus.BAD_REQUEST)
    , INVALID_PARAMETER     ("ERR002",   "invalid parameter",            HttpStatus.BAD_REQUEST)
    , INVALID_METHOD        ("ERR003",  "invalid http method",           HttpStatus.BAD_REQUEST)
    , ALREADY_EXISTS_MEMBER ("ERR004",  "already exists member",         HttpStatus.BAD_REQUEST)
    , NON_EXISTS_MEMBER     ("ERR005",  "non exists member",             HttpStatus.BAD_REQUEST)
    , INVALID_API_KEY       ("ERR006",  "invalid API Key",               HttpStatus.UNAUTHORIZED)
    , INTERNAL_SERVER_ERROR ("S99",     "internal server error",         HttpStatus.INTERNAL_SERVER_ERROR);

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    ErrorStatus(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

}
