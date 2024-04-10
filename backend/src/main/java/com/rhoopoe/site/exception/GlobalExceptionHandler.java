package com.rhoopoe.site.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({IOException.class})
    public ResponseEntity<String> handle(Exception exception, WebRequest request){
        log.error("I/O: " + exception.getMessage());
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class, InvalidFileNameException.class})
    public ResponseEntity<ProblemDetail> handle(ConstraintViolationException exception, WebRequest request){
        log.error("VALIDATION: " + exception.getLocalizedMessage());
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Request data field constraint violation");
        detail.setStatus(HttpStatus.BAD_REQUEST);
        detail.setInstance(URI.create(request.getContextPath()));
        return ResponseEntity.badRequest().body(detail);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ProblemDetail> handle(MethodArgumentNotValidException exception){
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Request data field constraint violation");
        detail.setStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(Objects.requireNonNull(exception.getDetailMessageArguments())[1].toString());
        return ResponseEntity.badRequest().body(detail);
    }

    @ExceptionHandler({AccountException.class})
    public ResponseEntity<ProblemDetail> handle(AccessDeniedException exception){
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Account exception");
        detail.setStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(exception.getLocalizedMessage());
        return ResponseEntity.badRequest().body(detail);
    }

    @ExceptionHandler({PostNotFoundException.class, MessageNotFoundException.class, NoSuchFileException.class})
    public ResponseEntity<String> handle(PostNotFoundException exception, WebRequest request){
        log.error("NOT FOUND: " + exception.getLocalizedMessage());
        return ResponseEntity.notFound().build();
    }
}