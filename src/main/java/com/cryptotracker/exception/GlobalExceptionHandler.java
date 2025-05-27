package com.cryptotracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.cryptotracker.exception.UserNotFoundException;
import com.cryptotracker.exception.EmailAlreadyExistsException;
import com.cryptotracker.exception.InvalidInputException;

@RestControllerAdvice
public class GlobalExceptionHandler{
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());	
	}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<String> handleEmailExists(EmailAlreadyExistsException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());	
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<String> handleInvalidInput(InvalidInputException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());	
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneric(Exception ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occured: "+ex.getMessage());
	}
}