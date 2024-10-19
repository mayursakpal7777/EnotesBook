package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?>handleException(Exception e)
	{
		log.error("GlobalException ::handleException ::",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?>handleNullPointerException(Exception e)
	{
		log.error("GlobalException ::handleNullPointerFoundException ::",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?>handleResourceNotFoundException(Exception e)
	{
		log.error("GlobalException ::handleResourceNotFoundException ::",e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		
	}

}
