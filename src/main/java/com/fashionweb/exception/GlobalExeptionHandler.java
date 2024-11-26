package com.fashionweb.exception;

import com.fashionweb.Model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<Response> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(new Response(false, "That bai", e.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<Response> handlingValidation(MethodArgumentNotValidException exception){
        return ResponseEntity.badRequest().body(new Response(false, "That bai", exception.getFieldError()));
    }
}
