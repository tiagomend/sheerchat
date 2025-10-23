package dev.tiagomendonca.sheerchat.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    private static final ErrorCode ERROR_CODE = ErrorCode.USERNAME_ALREADY_EXISTS;
    
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
    
    public ErrorCode getErrorCode() {
        return ERROR_CODE;
    }
}
