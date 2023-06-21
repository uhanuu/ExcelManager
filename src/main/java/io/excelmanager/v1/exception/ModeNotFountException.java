package io.excelmanager.v1.exception;

public class ModeNotFountException extends RuntimeException{
    public ModeNotFountException() {
        super();
    }

    public ModeNotFountException(String message) {
        super(message);
    }

    public ModeNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModeNotFountException(Throwable cause) {
        super(cause);
    }

    protected ModeNotFountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
