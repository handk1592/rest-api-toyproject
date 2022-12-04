package com.rest.edu.global.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
		logger.error(ex.getMessage());

		return makeExceptionResponseEntity(ex, request, ErrorStatus.INVALID_API_KEY);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(Exception ex, WebRequest request) {
		logger.error(ex.getMessage());

		return makeExceptionResponseEntity(ex, request, ErrorStatus.INVALID_FORMAT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ExceptionResponse> handleRequestArgValidExceptions(MethodArgumentNotValidException ex, WebRequest request) {
		logger.error(ex.getMessage());

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

	/**
	 * 지원하지 않은 HTTP method 호출
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private final ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(Exception ex, WebRequest request) {
		logger.error(ex.getMessage());
		return makeExceptionResponseEntity(ex, request, ErrorStatus.INVALID_METHOD);
	}

	/**
	 * CustomException 발생시
	 */
	@ExceptionHandler(BusinessException.class)
	private final ResponseEntity<ExceptionResponse> handleBusinessException(Exception ex, WebRequest request) {
		logger.error(((BusinessException) ex).getClass().getSimpleName());
		return makeExceptionResponseEntity(ex, request, ((BusinessException) ex).getErrorStatus());
	}

	/**
	 * 그 외 모든 Exception 발생시
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
		logger.error(ex.getMessage());

		return makeExceptionResponseEntity(ex, request, ErrorStatus.INTERNAL_SERVER_ERROR);
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
