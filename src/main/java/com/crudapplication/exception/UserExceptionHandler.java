package com.crudapplication.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserException.class)
	public final ResponseEntity<Object> handleUserException(UserException userExp) {

		ExceptionResponse expResponse = new ExceptionResponse();
		expResponse.setMessage(userExp.getMessage());
		expResponse.setTimestamp(LocalDateTime.now());
		expResponse.setHttpstatus((HttpStatus) userExp.getHttpStatus());
		return new ResponseEntity<Object>(expResponse, expResponse.getHttpstatus());
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception exp) {

		ExceptionResponse expResponse = new ExceptionResponse();
		expResponse.setMessage(exp.getMessage());
		expResponse.setTimestamp(LocalDateTime.now());
		expResponse.setHttpstatus(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<Object>(expResponse, expResponse.getHttpstatus());
	}

}
