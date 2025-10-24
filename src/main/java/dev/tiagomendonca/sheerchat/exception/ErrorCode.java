package dev.tiagomendonca.sheerchat.exception;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCode {
    EMAIL_ALREADY_EXISTS(EmailAlreadyExistsException.class),
    USERNAME_ALREADY_EXISTS(UsernameAlreadyExistsException.class),
    DATABASE_COMMUNICATION_ERROR(DatabaseCommunicationException.class);

    private final Class<? extends RuntimeException> exceptionClass;
    private static final Map<Class<? extends RuntimeException>, ErrorCode> classToCodeMap = new HashMap<>();

    static {
        for (ErrorCode code : ErrorCode.values()) {
            if (classToCodeMap.containsKey(code.exceptionClass)) {
                throw new IllegalStateException(
                    "Duplicate error code mapping detected for exception class: " + code.exceptionClass.getName()
                );
            }
            classToCodeMap.put(code.exceptionClass, code);
        }
    }

    ErrorCode(Class<? extends RuntimeException> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public Class<? extends RuntimeException> getExceptionClass() {
        return exceptionClass;
    }

    public static ErrorCode forException(Class<? extends RuntimeException> exceptionClass) {
        ErrorCode code = classToCodeMap.get(exceptionClass);
        if (code == null) {
            throw new IllegalArgumentException(
                "No error code registered for exception class: " + exceptionClass.getName()
            );
        }
        return code;
    }

    /**
     * Validates that the error code matches the exception class.
     * This ensures at runtime that exception classes use their designated error code.
     */
    public void validateFor(Class<? extends RuntimeException> exceptionClass) {
        if (!this.exceptionClass.equals(exceptionClass)) {
            throw new IllegalStateException(
                String.format("Error code %s is designated for %s but is being used by %s",
                    this.name(), this.exceptionClass.getName(), exceptionClass.getName())
            );
        }
    }
}
