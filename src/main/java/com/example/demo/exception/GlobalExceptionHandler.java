package com.example.demo.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.util.ValidationException;

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
/*	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?>handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
	{
		//log.error("GlobalException ::handleResourceNotFoundException ::",e.getMessage());
		//custome exception ligic 
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		Map<String, Object> error = new LinkedHashMap<>();
		allErrors.stream().forEach(er-> {
			String msg =er.getDefaultMessage();
			String field = ((FieldError) (er)).getField();
			error.put(field,  msg);
		});
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		
	}*/
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?>handleValidationException(ValidationException e)
	{
		//log.error("GlobalException ::handleResourceNotFoundException ::",e.getMessage());
		return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
		
	}

}
