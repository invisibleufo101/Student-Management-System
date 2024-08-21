package com.university.exceptions;

public class InvalidOptionException extends RuntimeException {

  public InvalidOptionException() {
    super("[숫자 1 - 5를 입력하세요.]");
  }
}
