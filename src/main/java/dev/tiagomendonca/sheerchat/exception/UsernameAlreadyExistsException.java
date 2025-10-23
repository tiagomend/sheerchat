package dev.tiagomendonca.sheerchat.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    private static final ErrorCode ERROR_CODE = ErrorCode.USERNAME_ALREADY_EXISTS;
    
    static {
        // Validate at class loading time that this exception uses the correct error code
        ERROR_CODE.validateFor(UsernameAlreadyExistsException.class);
    }
    
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
    
    public ErrorCode getErrorCode() {
        return ERROR_CODE;
    }
}
