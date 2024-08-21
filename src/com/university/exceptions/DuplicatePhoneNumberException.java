package com.university.exceptions;

public class DuplicatePhoneNumberException extends RuntimeException {

  public DuplicatePhoneNumberException() {
    super("[이미 존재하는 전화번호입니다. 다시 입력해주세요.]");
  }
}
