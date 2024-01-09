package com.rhoopoe.site.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({NoSuchFileException.class})
    public ResponseEntity<String> handle(NoSuchFileException exception, WebRequest request){
        log.error("File not found. Path: " + exception.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<String> handle(Exception exception, WebRequest request){
        log.error(exception.getMessage());
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }

}