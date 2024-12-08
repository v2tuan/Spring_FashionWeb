package com.fashionweb.exception;

import com.fashionweb.Model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Component
public class GlobalExeptionHandler {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<Response> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(new Response(false, "That bai", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Nếu có lỗi validate, trả về các lỗi chi tiết (bao gồm tên trường và thông báo lỗi)
        BindingResult bindingResult = ex.getBindingResult();

            List<Map<String, String>> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> {
                        Map<String, String> error = new HashMap<>();
                        error.put("field", fieldError.getField()); // Tên trường
                        error.put("message", fieldError.getDefaultMessage()); // Thông báo lỗi
                        return error;
                    })
                    .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new Response(false, "Validation failed", errorMessages));
    }
}
