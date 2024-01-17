package com.rhoopoe.site.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    @ExceptionHandler({IOException.class})
//    public ResponseEntity<String> handle(Exception exception, WebRequest request){
//        log.error("I/O: " + exception.getMessage());
//        return ResponseEntity.internalServerError().body(exception.getMessage());
//    }

    @ExceptionHandler({ConstraintViolationException.class, InvalidFileNameException.class})
    public ResponseEntity<Object> handle(ConstraintViolationException exception, WebRequest request){
        log.error("VALIDATION: " + exception.getLocalizedMessage());
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handle(MethodArgumentNotValidException exception, WebRequest request){
        String message = Objects.requireNonNull(exception.getDetailMessageArguments()).length > 0 ?
               exception.getDetailMessageArguments()[1].toString() : "Invalid request";
        log.error("VALIDATION: " + message);
        return ResponseEntity.badRequest().body(message);
    }

//    @ExceptionHandler({PostNotFoundException.class, MessageNotFoundException.class, NoSuchFileException.class})
//    public ResponseEntity<String> handle(PostNotFoundException exception, WebRequest request){
//        log.error("NOT FOUND: " + exception.getLocalizedMessage());
//        return ResponseEntity.notFound().build();
//    }


}