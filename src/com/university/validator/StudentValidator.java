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
   * Catch Runtime Exceptions that are custom tailored for the current situation
   *
   */

  public StudentValidator() {}

  public int validateOption(int option) throws InvalidOptionException {
    if (option < 1 || option > 5) {
      throw new InvalidOptionException();
    }
    return option;
  }

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

//  public int validateStudentId(int studentId, Student student){
//
//  }

//  public int checkValidStudentId() {
//    while (true) {
//      try {
//        System.out.print("학\t번 입력 : ");
//
//        String input = this.scanner.next();
//        if (input.length() != 8) {
//          throw new StudentIdFormatException();
//        }
//        int studentId = Integer.parseInt(input);
//        if (Objects.isNull(this.studentService.getStudentById(studentId))) {
//          throw new InvalidStudentIdException();
//        }
//        return studentId;
//      } catch (NumberFormatException nfe) {
//        System.out.println("[올바른 형식으로 입력하세요 (숫자 8자리). 다시 입력하세요.]");
//      } catch (StudentIdFormatException | InvalidStudentIdException e) {
//        System.out.println(e.getMessage());
//      }
//    }
//  }

  public String validateName(String name) throws NameFormatException {
    String namePattern = "^[가-힣]{2,7}$"; // chatGPT가 제공한 Regex Pattern
    if (!name.matches(namePattern)){
      throw new NameFormatException();
    }
    return name;
  }

  public String validateMajor(String major) throws InvalidMajorException {
    String majorPattern = "^[가-힣]+학과$"; // chatGPT가 제공한 Regex Pattern
    if (!major.matches(majorPattern)){
      throw new InvalidMajorException();
    }
    return major;
  }

  public String validatePhoneNumber(String phoneNumber, LinkedHashMap<Integer,Student>students) throws PhoneNumberFormatException, DuplicatePhoneNumberException{
    if (isValidPhoneNumberFormat(phoneNumber)){
      throw new PhoneNumberFormatException();
    }
    if (isDuplicatePhoneNumber(phoneNumber, students)){
      throw new DuplicatePhoneNumberException();
    }
    return phoneNumber;
  }

//  public String validatePhoneNumber(String phoneNumber, Student student){
//
//  }
//  public String validatePhoneNumber(int studentId) {
//    while (true) {
//      try {
//        System.out.print("전화번호 입력 : ");
//
//        String input = this.scanner.next();
//        if (isValidPhoneNumberFormat(input)) {
//          throw new PhoneNumberFormatException();
//        }
//
//        Student currentStudent = this.studentService.getStudentById(studentId);
//        currentStudent.setPhoneNumber(null);
//        if (!input.equals(currentStudent.getPhoneNumber()) && isDuplicatePhoneNumber(input)){
//          if (isDuplicatePhoneNumber(input)){
//            throw new DuplicatePhoneNumberException();
//          }
//        }
//
//        return input;
//      } catch (PhoneNumberFormatException pnfe) {
//        System.out.println(pnfe.getMessage());
//      }
//    }
//  }

  private boolean isValidPhoneNumberFormat(String phoneNumber) {
    String phoneNumberPattern = "010-\\d{4}-\\d{4}";
    return !phoneNumber.matches(phoneNumberPattern);
  }

  // Code provided by ChatGPT
  private boolean isDuplicatePhoneNumber(String phoneNumber, LinkedHashMap<Integer,Student>students) {
    return students.values().stream().anyMatch(student -> student.getPhoneNumber().equals(phoneNumber));
  }
}