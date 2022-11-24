package net.backend.paylens.exception;

import net.backend.paylens.exception.custom.CustomBadRequestException;
import net.backend.paylens.exception.custom.CustomNotFoundException;
import net.backend.paylens.exception.custom.CustomUnauthorizedException;
import net.backend.paylens.model.dto.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    // Attribute
    private ErrorMessage<Object> errorMessage;
    private Map<Object, Object> errors;

    // Exception global
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleExceptionGlobal(Exception e) {
        errorMessage = new ErrorMessage<Object>(HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    // Not found exception
    @ExceptionHandler(value = CustomNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(CustomNotFoundException e) {
        errorMessage = new ErrorMessage<Object>(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    // Bad request exception
    @ExceptionHandler(value = CustomBadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(CustomBadRequestException e) {
        errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    // Unauthorized exception
    @ExceptionHandler(value = CustomUnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(CustomUnauthorizedException e) {
        errorMessage = new ErrorMessage<Object>(HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    // Argument not valid exception
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        errors = new HashMap<>();
        e.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), "Error validation", errors);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }
}
