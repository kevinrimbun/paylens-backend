package net.backend.paylens.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.backend.paylens.model.dto.response.ErrorMessage;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {
    private ErrorMessage<Object> errorMessage;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException e) {
      errorMessage = new ErrorMessage<Object>(HttpStatus.EXPECTATION_FAILED.value(), LocalDateTime.now(),
          "File too large!", null);
      return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }
}
