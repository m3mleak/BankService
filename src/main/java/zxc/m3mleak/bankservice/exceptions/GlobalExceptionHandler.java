package zxc.m3mleak.bankservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(
            String message,
            String path,
            LocalDateTime timestamp,
            List<FieldError> erros
    ) {
        public record FieldError(String field, String message) {}
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(AccountNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), null, LocalDateTime.now(), List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), null, LocalDateTime.now(), List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream().
                map(e -> new ErrorResponse.FieldError(e.getField(), e.getDefaultMessage())).toList();

        ErrorResponse response = new ErrorResponse("Validation failed", null, LocalDateTime.now(), fieldErrors);
        return ResponseEntity.badRequest().body(response);
    }
}
