package dev.tiagomendonca.sheerchat.exception;

public class DatabaseCommunicationException extends RuntimeException {
    public DatabaseCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
