package dev.tiagomendonca.sheerchat.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    private static final String ERROR_CODE = "USERNAME_ALREADY_EXISTS";
    
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
    
    public String getErrorCode() {
        return ERROR_CODE;
    }
}
