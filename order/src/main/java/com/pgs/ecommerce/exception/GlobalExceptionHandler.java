package com.pgs.ecommerce.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handle(EntityNotFoundException exp) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(exp.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
    Map<String, String> errors = new HashMap<String, String>();
    exp.getBindingResult().getAllErrors()
            .forEach(error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    return ResponseEntity
            .status(BAD_REQUEST)
            .body(new ErrorResponse(errors));
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<String> handle(BusinessException exp) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exp.getMsg());
  }
  
  @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.BadRequest.class)
  public ResponseEntity<String> handle(org.springframework.web.client.HttpClientErrorException.BadRequest exp) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(exp.getResponseBodyAsString());
  }
  
  @ExceptionHandler(feign.FeignException.NotFound.class)
  public ResponseEntity<String> handle(feign.FeignException.NotFound exp) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("Customer not found or invalid ID provided");
  }
  
  @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
  public ResponseEntity<String> handleConflict(DataIntegrityViolationException exp) {
      return ResponseEntity
          .status(HttpStatus.CONFLICT) // HTTP 409
          .body("The reference to the given order already exist.");
  }
}