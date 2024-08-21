package com.university.exceptions;

public class InvalidStudentIdException extends RuntimeException {

  public InvalidStudentIdException() {
    super("[일치하는 학번이 없습니다.]");
  }
}
