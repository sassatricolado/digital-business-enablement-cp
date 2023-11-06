package br.com.fiap.taskapi.config;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var exceptions = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(exceptions.stream().map(ValidationExceptionData::new).toList());
    }

    private record ValidationExceptionData(String field, String msg) {
        public ValidationExceptionData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
