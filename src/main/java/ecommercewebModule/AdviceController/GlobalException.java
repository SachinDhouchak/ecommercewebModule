package ecommercewebModule.AdviceController;

import ecommercewebModule.CustomException.ErrorResponse;
import ecommercewebModule.CustomException.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = new ErrorResponse(message,status, LocalDateTime.now(),false);
         return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
