package com.decathlon.alert.exception.handler;

import com.decathlon.alert.exception.ApiError;
import com.decathlon.alert.exception.TeamAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// error handle for @Valid
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers,
																  HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		//Get all errors
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);

	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError =
				new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList()));
		return new ResponseEntity<Object>(
				apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({TeamAlreadyExistsException.class})
	public ResponseEntity<Object> handleException(TeamAlreadyExistsException e) {
		ApiError apiError =
				new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<Object>(
				apiError, new HttpHeaders(), apiError.getStatus());
	}
}
