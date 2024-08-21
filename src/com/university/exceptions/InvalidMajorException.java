package com.university.exceptions;

public class InvalidMajorException extends RuntimeException {

  public InvalidMajorException() {
    super("[존재하지 않는 학과입니다.]");
  }
}
