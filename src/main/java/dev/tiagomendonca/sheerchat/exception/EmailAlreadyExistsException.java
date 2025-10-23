package dev.tiagomendonca.sheerchat.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    private static final ErrorCode ERROR_CODE = ErrorCode.EMAIL_ALREADY_EXISTS;
    
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    
    public ErrorCode getErrorCode() {
        return ERROR_CODE;
    }
}
