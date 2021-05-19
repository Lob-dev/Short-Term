package com.lob.app.global.exception;

import com.lob.app.url.domain.exception.NoSuchURLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(value = ConstraintViolationException.class)
	protected ResponseEntity constraintViolationExceptionHandler() {
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(value = NoSuchURLException.class)
	protected ResponseEntity noSuchURLExceptionExceptionHandler() {
		return ResponseEntity.badRequest().build();
	}
}
