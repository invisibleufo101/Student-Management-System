package com.university.exceptions;

public class PhoneNumberFormatException extends RuntimeException {

  public PhoneNumberFormatException() {
    super("[형식에 맞게 전화번호를 입력해주세요. (010-XXXX-XXXX)]");
  }

}
