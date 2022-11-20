package com.rest.edu.global.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestResponseExceptionHandler.class);

	@ExceptionHandler(AuthenticationException.class)
	public final ResponseEntity<ExceptionResponse> handleAuthenticationException(Exception ex, WebRequest request) {
		logger.error(ex.toString());

		return makeExceptionResponseEntity(ex, request, ErrorStatus.INVALID_API_KEY);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
		logger.error(ex.toString());

		return makeExceptionResponseEntity(ex, request, ErrorStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ExceptionResponse> handleRequestArgValidExceptions(MethodArgumentNotValidException ex, WebRequest request) {
		logger.error(ex.toString());

		BindingResult bindingResult = ex.getBindingResult();
		String errorDescription = "";

		if(bindingResult.hasErrors()) {
			errorDescription = ex.getBindingResult().getAllErrors()
								.stream()
								.map(e -> "[" + ((FieldError) e).getField() + "] " +  e.getDefaultMessage())
								.collect(Collectors.joining(", "));
		}

		return makeExceptionResponseEntity(ex, request, ErrorStatus.INVALID_PARAMETER, errorDescription);
	}

	private ResponseEntity<ExceptionResponse> makeExceptionResponseEntity(Exception ex, WebRequest request, ErrorStatus errorStatus) {
		ExceptionResponse exceptionResponse = ExceptionResponse.builder()
												.errorCode(errorStatus.getErrorCode())
												.errorMessage(errorStatus.getErrorMessage())
												.build();

		return new ResponseEntity(exceptionResponse, errorStatus.getHttpStatus());
	}

	private ResponseEntity<ExceptionResponse> makeExceptionResponseEntity(Exception ex, WebRequest request, ErrorStatus errorStatus, String errorDescription) {
		ExceptionResponse exceptionResponse = ExceptionResponse.builder()
												.errorCode(errorStatus.getErrorCode())
												.errorMessage(errorStatus.getErrorMessage())
												.errorDescription(errorDescription)
												.build();

		return new ResponseEntity(exceptionResponse, errorStatus.getHttpStatus());
	}

}
