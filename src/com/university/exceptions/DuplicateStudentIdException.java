package com.university.exceptions;

public class DuplicateStudentIdException extends RuntimeException {

  public DuplicateStudentIdException() {
    super("[동일한 학번입니다. 다시 입력하세요.]");
  }
}
