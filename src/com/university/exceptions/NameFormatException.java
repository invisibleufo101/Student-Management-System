package com.university.exceptions;

public class NameFormatException extends RuntimeException {

  public NameFormatException() {
    super("[입력이 형식에 맞지 않습니다. 2글자에서 7글자 사이 한글로 입력하세요.]");
  }
}
