package dev.tiagomendonca.sheerchat.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    private static final ErrorCode ERROR_CODE = ErrorCode.EMAIL_ALREADY_EXISTS;
    
    static {
        // Validate at class loading time that this exception uses the correct error code
        ERROR_CODE.validateFor(EmailAlreadyExistsException.class);
    }
    
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    
    public ErrorCode getErrorCode() {
        return ERROR_CODE;
    }
}
