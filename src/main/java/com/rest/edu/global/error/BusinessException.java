package com.rest.edu.global.error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private ErrorStatus errorStatus;

    public BusinessException(ErrorStatus errorStatus) {
        super(errorStatus.getErrorMessage());
        this.errorStatus = errorStatus;
    }

    public BusinessException(String message, ErrorStatus errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }
}
