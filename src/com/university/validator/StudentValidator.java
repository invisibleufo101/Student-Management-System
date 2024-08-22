package com.university.validator;

import com.university.exceptions.DuplicatePhoneNumberException;
import com.university.exceptions.DuplicateStudentIdException;
import com.university.exceptions.InvalidMajorException;
import com.university.exceptions.InvalidOptionException;
import com.university.exceptions.InvalidStudentIdException;
import com.university.exceptions.NameFormatException;
import com.university.exceptions.PhoneNumberFormatException;
import com.university.exceptions.StudentIdFormatException;

import com.university.model.Student;

import java.util.LinkedHashMap;
import java.util.Objects;

public class StudentValidator {

  /**
   * Java의 기존 RuntimeException이 처리하지 못하는 예외를 확인하고 던지는 클래스.
   */
  public StudentValidator() {}

  /**
   * 사용자가 StudentManagementSystem에 입력하는 관리번호가 숫자 1 - 5 사이인지 확인함.
   *
   * @param option 사용자가 입력하는 관리 번호
   * @return 숫자 1 - 5 사이의 정수
   * @throws InvalidOptionException 숫자 1 - 5 사이가 아닐 경우의 예외.
   */
  public int validateOption(int option) throws InvalidOptionException {
    if (option < 1 || option > 5) {
      throw new InvalidOptionException();
    }
    return option;
  }

  /**
   * 
   * @param studentId 사용자가 입력하는 학번.
   * @param students 프로그램에 등록된 전체 학생들의 객체들.
   * @return 입력된 학번.
   * @throws StudentIdFormatException 학번 길이가 8자리가 아닐 경우의 예외.
   * @throws DuplicateStudentIdException 입력하는 학번이 다른 학생들과 중복될 경우의 예외.
   */
  public int validateStudentId(int studentId, LinkedHashMap<Integer,Student>students)
    throws StudentIdFormatException, DuplicateStudentIdException{
    if (String.valueOf(studentId).length() != 8){
      throw new StudentIdFormatException();
    }
    if (students.containsKey(studentId)){
      throw new DuplicateStudentIdException();
    }
    return studentId;
  }

  public int validateStudentId(int studentId, Student student) throws StudentIdFormatException, InvalidStudentIdException{
    if (String.valueOf(studentId).length() != 8){
      throw new StudentIdFormatException();
    }
    if (Objects.isNull(student)){
      throw new InvalidStudentIdException();
    }
    return studentId;
  }

  /**
   * 등록하는 이름은 최대 7글자까지 등록되도록 예외처리를 함.
   * 1993년부터 법적으로 성을 제외한 이름은 5글자 이내로 쓰게 만듬.
   * 하지만 성이 한 글자가 아닌 두 글자 성이 있을 경우를 위해 7글자 제한을 둠.
   *
   * @param name 등록하는 이름
   * @return 예외처리된 이름
   * @throws NameFormatException 정규식 표현에 맞지 않는 경우의 예외.
   */
  public String validateName(String name) throws NameFormatException {
    String namePattern = "^[가-힣]{2,7}$"; // chatGPT가 제공한 Regex Pattern
    if (!name.matches(namePattern)){
      throw new NameFormatException();
    }
    return name;
  }

  /**
   * 등록하는 학과명은 한글로만 입력하고 "학과"로 끝나도록 정규식 표현을 씀.
   *
   * @param major 사용자가 입력한 학과
   * @return 예외처리된 학과명
   * @throws InvalidMajorException 정규식 표현에 맞지 않을 경우 예외를 던짐.
   */
  public String validateMajor(String major) throws InvalidMajorException {
    String majorPattern = "^[가-힣]+학과$"; // chatGPT가 제공한 Regex Pattern
    if (!major.matches(majorPattern)){
      throw new InvalidMajorException();
    }
    return major;
  }

  /**
   * 등록하는 학생 전화번호는 "010-XXXX-XXXX" 패턴으로 등록되도록 정규식 표현을 씀.
   * 또한, 전화번호가 이미 등록된 학생들과 겹치지 않도록 확인함.
   *
   * @param phoneNumber 등록하는 전화번호
   * @param students 프로그램에 등록된 전체 학생들
   * @return 예외처리된 전화번호
   * @throws PhoneNumberFormatException "010-XXXX-XXXX" 패턴에 맞지 않으면 예외를 던짐.
   * @throws DuplicatePhoneNumberException 다른 학생들의 전화번호와 중복되면 예외를 던짐.
   */
  public String validatePhoneNumber(String phoneNumber, LinkedHashMap<Integer,Student>students) throws PhoneNumberFormatException, DuplicatePhoneNumberException{
    if (!isValidPhoneNumberFormat(phoneNumber)){
      throw new PhoneNumberFormatException();
    }
    if (isDuplicatePhoneNumber(phoneNumber, students)){
      throw new DuplicatePhoneNumberException();
    }
    return phoneNumber;
  }

  /**
   * 학생 정보를 업데이트할 때 전화번호는 등록된 다른 학생들의 전화번호와 중복되면 안됨.
   * 전체 학생들을 하나씩 iterate하면서 업데이트 될 전화번호가 중복되는지 확인함.
   *
   * 만약 사용자가 학생 전화번호를 업데이트하지 않고 그대로 쓸 경우
   * 중복되는 전화번호가 업데이트 되는 학생 객체인지 확인함.
   *
   * @param phoneNumber 사용자 입력으로 바꿀 전화번호
   * @param updatedStudent 업데이트하는 학생 객체
   * @param students 프로그램에 등록된 전체 학생 객체들
   * @return 예외처리된 전화번호
   */
  public String validatePhoneNumber(String phoneNumber, Student updatedStudent, LinkedHashMap<Integer,Student>students) throws PhoneNumberFormatException, DuplicatePhoneNumberException{
    if (!isValidPhoneNumberFormat(phoneNumber)){
      throw new PhoneNumberFormatException();
    }

   for (Student student : students.values()){
     if (!student.equals(updatedStudent) && student.getPhoneNumber().equals(phoneNumber)){
       throw new DuplicatePhoneNumberException();
     }
   }
   return phoneNumber;
  }

  private boolean isValidPhoneNumberFormat(String phoneNumber) {
    String phoneNumberPattern = "010-\\d{4}-\\d{4}";
    return phoneNumber.matches(phoneNumberPattern);
  }

  // Code provided by ChatGPT
  private boolean isDuplicatePhoneNumber(String phoneNumber, LinkedHashMap<Integer,Student>students) {
    return students.values().stream().anyMatch(student -> student.getPhoneNumber().equals(phoneNumber));
  }
}