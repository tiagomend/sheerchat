package dev.tiagomendonca.sheerchat.dto;

public class RegisterResponse {
    private String message;
    private Long userId;
    private String username;
    private boolean emailConfirmationSent;
    private String errorCode;

    public RegisterResponse() {
    }

    public RegisterResponse(String message, Long userId, String username, boolean emailConfirmationSent) {
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.emailConfirmationSent = emailConfirmationSent;
    }

    public RegisterResponse(String message, Long userId, String username, boolean emailConfirmationSent, String errorCode) {
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.emailConfirmationSent = emailConfirmationSent;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEmailConfirmationSent() {
        return emailConfirmationSent;
    }

    public void setEmailConfirmationSent(boolean emailConfirmationSent) {
        this.emailConfirmationSent = emailConfirmationSent;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
