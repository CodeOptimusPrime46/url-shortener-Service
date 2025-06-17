package com.codemonkey.url.shortener.dto.exception.handler;


import com.codemonkey.url.shortener.dto.exception.NoUrlFoundException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

@RestControllerAdvice
@Slf4j
public class RequestExceptionHandler {

  @ExceptionHandler(NoUrlFoundException.class)
  public ResponseEntity handleNoUrlFoundException(ServerWebExchange request, NoUrlFoundException ex) {
    final Map<String, Object> body = new HashMap<>();
    body.put("timestamp", java.time.Instant.now().toString());
    body.put("message", ex.getMessage());
    body.put("status", HttpStatus.NOT_FOUND.value());
    body.put("url", request.getRequest().getPath().value());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }
}
