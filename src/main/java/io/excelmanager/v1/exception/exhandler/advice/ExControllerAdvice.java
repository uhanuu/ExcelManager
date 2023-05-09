package io.excelmanager.v1.exception.exhandler.advice;

import io.excelmanager.v1.exception.ExcelFileException;
import io.excelmanager.v1.exception.ModeNotFountException;
import io.excelmanager.v1.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice(basePackages = "io.excelmanager.v1.excel.controller")
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> excelExHandler(ExcelFileException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult classHandler(ClassNotFoundException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("CLASS-EX","DB 접속오류");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult modeHandler(ModeNotFountException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("MODE-EX","MODE설정 오류");
    }

    @ExceptionHandler
    public ErrorResult sqlHandler(SQLException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("SQL-EX","sql 접속오류");
    }

}
