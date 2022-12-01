package net.backend.paylens.exception;

import net.backend.paylens.exception.custom.CustomBadRequestException;
import net.backend.paylens.exception.custom.CustomNotFoundException;
import net.backend.paylens.exception.custom.CustomUnauthorizedException;
import net.backend.paylens.model.dto.response.ErrorMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    // Attribute
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    private ErrorMessage<Object> errorMessage;
    private Map<Object, Object> errors;

    // Exception global
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleExceptionGlobal(Exception e) {
        // errorMessage = new ErrorMessage<Object>(HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), e.getMessage(), null);
        // return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);

        LOGGER.error("Exception: {}", e.getMessage());

        errorMessage = new ErrorMessage<Object>(HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(),
            e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    // Not found exception
    @ExceptionHandler(value = CustomNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(CustomNotFoundException e) {
        // errorMessage = new ErrorMessage<Object>(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), null);
        // return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);

        LOGGER.error("Error not found: {}", e.getMessage());

        errorMessage = new ErrorMessage<Object>(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(),
            e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    // Bad request exception
    @ExceptionHandler(value = CustomBadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(CustomBadRequestException e) {
        // errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), e.getMessage(), null);
        // return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);

        LOGGER.error("Error bad request: {}", e.getMessage());

        errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
            e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    // Unauthorized exception
    @ExceptionHandler(value = CustomUnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(CustomUnauthorizedException e) {
        // errorMessage = new ErrorMessage<Object>(HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now(), e.getMessage(), null);
        // return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);

        LOGGER.error("Error unauthorized: {}", e.getMessage());

        errorMessage = new ErrorMessage<Object>(HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now(),
            e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    // Argument not valid exception
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        // errors = new HashMap<>();
        // e.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        // errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), "Error validation", errors);
        // return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);

        LOGGER.error("Method argument not valid: {}", e.getFieldError());

        errors = new HashMap<>();
    
        e.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    
        errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), "Error validation",
            errors);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    @ExceptionHandler(value = JWTCreationException.class)
    public ResponseEntity<Object> handleJWTCreation(JWTCreationException e) {
        LOGGER.error("Invalid signing configuration: {}", e.getMessage());
  
        errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_GATEWAY.value(), LocalDateTime.now(), e.getMessage(),
          null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }
  
    @ExceptionHandler(value = JWTDecodeException.class)
    public ResponseEntity<Object> handleJWTDecode(JWTDecodeException e) {
        LOGGER.error("Invalid token: {}", e.getMessage());
  
        errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), e.getMessage(),
          null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }
  
    @ExceptionHandler(value = JWTVerificationException.class)
    public ResponseEntity<Object> handleJWTVerification(JWTVerificationException e) {
      LOGGER.error("Invalid signature: {}", e.getMessage());
  
      errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), e.getMessage(),
          null);
      return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }
  
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException e) {
        LOGGER.error("Error username not found: {}", e.getMessage());
  
        errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), e.getMessage(),
          null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }
}
