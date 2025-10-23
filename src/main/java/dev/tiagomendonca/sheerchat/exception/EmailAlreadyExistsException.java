package dev.tiagomendonca.sheerchat.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    private static final String ERROR_CODE = "EMAIL_ALREADY_EXISTS";
    
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    
    public String getErrorCode() {
        return ERROR_CODE;
    }
}
