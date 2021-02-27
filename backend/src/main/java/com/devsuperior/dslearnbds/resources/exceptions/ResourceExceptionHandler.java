package com.devsuperior.dslearnbds.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//import com.amazonaws.AmazonClientException;
//import com.amazonaws.AmazonServiceException;
import com.devsuperior.dslearnbds.services.exceptions.DatabaseException;
import com.devsuperior.dslearnbds.services.exceptions.ForbiddenException;
import com.devsuperior.dslearnbds.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dslearnbds.services.exceptions.UnauthorizedException;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
    	StandardError err = new StandardError();
    	HttpStatus status = HttpStatus.NOT_FOUND;
    	
    	err.setTimestamp(Instant.now());
    	err.setStatus(status.value());
    	err.setError("Resource not found");
    	err.setMessage(e.getMessage());
    	err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }
    
    @ExceptionHandler(DatabaseException.class)
   	public ResponseEntity<StandardError> entityNotFound(DatabaseException e, HttpServletRequest request){
       	StandardError err = new StandardError();
       	HttpStatus status = HttpStatus.BAD_REQUEST;
       	
       	err.setTimestamp(Instant.now());
       	err.setStatus(status.value());
       	err.setError("Database Exception");
       	err.setMessage(e.getMessage());
       	err.setPath(request.getRequestURI());
   		return ResponseEntity.status(status).body(err);
       }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
   	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
       	ValidationError err = new ValidationError();
       	HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
       	
       	err.setTimestamp(Instant.now());
       	err.setStatus(status.value());
       	err.setError("Validation Exception");
       	err.setMessage(e.getMessage());
       	err.setPath(request.getRequestURI());
       	
       	for(FieldError f : e.getBindingResult().getFieldErrors() ) {
       		err.addError(f.getField(), f.getDefaultMessage());
       	}
       	
   		return ResponseEntity.status(status).body(err);
       }
    
    
    @ExceptionHandler(ForbiddenException.class)
   	public ResponseEntity<OAuthCustomError> forbidden(ForbiddenException e, HttpServletRequest request){
    	OAuthCustomError err = new OAuthCustomError("forbidden", e.getMessage());       	  	
   		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
       }
    
    @ExceptionHandler(UnauthorizedException.class)
   	public ResponseEntity<OAuthCustomError> unauthorized(UnauthorizedException e, HttpServletRequest request){
    	OAuthCustomError err = new OAuthCustomError("Unauthorized", e.getMessage());       	  	
   		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
       }
    /*
    @ExceptionHandler(AmazonServiceException.class)
   	public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request){
       	StandardError err = new StandardError();
       	HttpStatus status = HttpStatus.BAD_REQUEST;
       	
       	err.setTimestamp(Instant.now());
       	err.setStatus(status.value());
       	err.setError("AWS Exception");
       	err.setMessage(e.getMessage());
       	err.setPath(request.getRequestURI());
   		return ResponseEntity.status(status).body(err);
       }
    
    @ExceptionHandler(AmazonClientException.class)
   	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request){
       	StandardError err = new StandardError();
       	HttpStatus status = HttpStatus.BAD_REQUEST;
       	
       	err.setTimestamp(Instant.now());
       	err.setStatus(status.value());
       	err.setError("AWS Exception");
       	err.setMessage(e.getMessage());
       	err.setPath(request.getRequestURI());
   		return ResponseEntity.status(status).body(err);
       }
    */
    @ExceptionHandler(IllegalArgumentException.class)
   	public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e, HttpServletRequest request){
       	StandardError err = new StandardError();
       	HttpStatus status = HttpStatus.BAD_REQUEST;
       	
       	err.setTimestamp(Instant.now());
       	err.setStatus(status.value());
       	err.setError("BAD request");
       	err.setMessage(e.getMessage());
       	err.setPath(request.getRequestURI());
   		return ResponseEntity.status(status).body(err);
       }
}
