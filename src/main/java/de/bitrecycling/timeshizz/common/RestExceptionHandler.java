package de.bitrecycling.timeshizz.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.ResponseEntity.notFound;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {    @ExceptionHandler(value = {ResourceNotFoundException.class})
public ResponseEntity resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
    log.info(ex.getLocalizedMessage());
    return notFound().build();
}
}