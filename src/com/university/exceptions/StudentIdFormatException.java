package com.university.exceptions;

public class StudentIdFormatException extends RuntimeException {

  public StudentIdFormatException() {
    super("[학번은 숫자 8자리입니다. 다시 입력하세요.]");
  }
}
