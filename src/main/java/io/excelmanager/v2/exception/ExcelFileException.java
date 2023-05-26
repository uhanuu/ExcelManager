package io.excelmanager.v2.exception;

public class ExcelFileException extends RuntimeException {

    public ExcelFileException() {
        super();
    }

    public ExcelFileException(String message) {
        super(message);
    }

    public ExcelFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelFileException(Throwable cause) {
        super(cause);
    }

    protected ExcelFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
