package com.alurachallenge.forohub.utils;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ResponseErrorValidation>> handle400(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors().stream().map(ResponseErrorValidation::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    public record ResponseErrorValidation(String campo, String error){
        public ResponseErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}
