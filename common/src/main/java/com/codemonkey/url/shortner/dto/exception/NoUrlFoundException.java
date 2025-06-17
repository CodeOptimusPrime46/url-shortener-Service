package com.codemonkey.url.shortner.dto.exception;

public class NoUrlFoundException extends RuntimeException {

  public NoUrlFoundException(String message) {
    super(message);
  }
}
