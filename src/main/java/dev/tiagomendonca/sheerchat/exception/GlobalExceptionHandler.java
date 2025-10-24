package dev.tiagomendonca.sheerchat.exception;

import dev.tiagomendonca.sheerchat.dto.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<RegisterResponse> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        RegisterResponse errorResponse = new RegisterResponse(
            e.getMessage(), 
            null, 
            null, 
            false, 
            e.getErrorCode().name()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<RegisterResponse> handleUsernameAlreadyExists(UsernameAlreadyExistsException e) {
        RegisterResponse errorResponse = new RegisterResponse(
            e.getMessage(), 
            null, 
            null, 
            false, 
            e.getErrorCode().name()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DatabaseCommunicationException.class)
    public ResponseEntity<RegisterResponse> handleDatabaseCommunicationException(DatabaseCommunicationException e) {
        RegisterResponse errorResponse = new RegisterResponse(
            e.getMessage(), 
            null, 
            null, 
            false
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
