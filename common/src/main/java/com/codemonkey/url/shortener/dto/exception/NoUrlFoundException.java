package com.codemonkey.url.shortener.dto.exception;

public class NoUrlFoundException extends RuntimeException {

  public NoUrlFoundException(String message) {
    super(message);
  }
}
