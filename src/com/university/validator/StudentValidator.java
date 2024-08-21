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

  public int validateOption(int option) {
    if (option < 1 || option > 5) {
      throw new InvalidOptionException();
    }
    return option;
  }

  public int validateStudentId(int studentId, LinkedHashMap<Integer,Student>students){
    if (String.valueOf(studentId).length() != 8){
      throw new StudentIdFormatException();
    }

    if (Objects.isNull(this.))
    if (students.containsKey(studentId)){
      throw new DuplicateStudentIdException();
    }
    return studentId;
  }

  public int checkValidStudentId() {
    while (true) {
      try {
        System.out.print("학\t번 입력 : ");

        String input = this.scanner.next();
        if (input.length() != 8) {
          throw new StudentIdFormatException();
        }
        int studentId = Integer.parseInt(input);
        if (Objects.isNull(this.studentService.getStudentById(studentId))) {
          throw new InvalidStudentIdException();
        }
        return studentId;
      } catch (NumberFormatException nfe) {
        System.out.println("[올바른 형식으로 입력하세요 (숫자 8자리). 다시 입력하세요.]");
      } catch (StudentIdFormatException | InvalidStudentIdException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public String validateName() {
    String namePattern = "^[가-힣]{2,7}$"; // chatGPT가 제공한 Regex Pattern
    while (true) {
      try {
        System.out.print("이\t름 입력 : ");

        String input = this.scanner.next();
        if (!input.matches(namePattern)) {
          throw new NameFormatException();
        }
        return input;
      } catch (NameFormatException nfe) {
        System.out.println(nfe.getMessage());
      }
    }
  }

  public String validateMajor() {
    String majorPattern = "^[가-힣]+학과$"; // chatGPT가 제공한 Regex Pattern
    while (true) {
      try {
        System.out.print("학\t과 입력 : ");

        String input = scanner.next();
        if (!input.matches(majorPattern)) {
          throw new InvalidMajorException();
        }
        return input;
      } catch (InvalidMajorException ime) {
        System.out.println(ime.getMessage());
      }
    }
  }

  public String validatePhoneNumber() {
    while (true) {
      try {
        System.out.print("전화번호 입력 : ");

        String input = this.scanner.next();
        if (isValidPhoneNumberFormat(input)) {
          throw new PhoneNumberFormatException();
        }
        if (isDuplicatePhoneNumber(input)) {
          throw new DuplicatePhoneNumberException();
        }
        return input;
      } catch (PhoneNumberFormatException | DuplicatePhoneNumberException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Overloaded method used with updateStudent method in StudentManagementSystem class.
   * Determines DuplicatePhoneNumberException differently because the phoneNumber can remain
   * the same for the student currently being updated but must not be a duplicate of other students.
   *
   * @param studentId the identifier that specifies the student that is being updated
   * @return phoneNumber validated from user input
   */
  public String validatePhoneNumber(int studentId) {
    while (true) {
      try {
        System.out.print("전화번호 입력 : ");

        String input = this.scanner.next();
        if (isValidPhoneNumberFormat(input)) {
          throw new PhoneNumberFormatException();
        }

        Student currentStudent = this.studentService.getStudentById(studentId);
        currentStudent.setPhoneNumber(null);
        if (!input.equals(currentStudent.getPhoneNumber()) && isDuplicatePhoneNumber(input)){
          if (isDuplicatePhoneNumber(input)){
            throw new DuplicatePhoneNumberException();
          }
        }

        return input;
      } catch (PhoneNumberFormatException pnfe) {
        System.out.println(pnfe.getMessage());
      }
    }
  }

  public String validateTerminationResponse(){
    while (true) {
      System.out.print("프로그램을 종료하시겠습니까? (y/n): ");

      String response = this.scanner.next();
      if (!response.equals("y") && !response.equals("n")){
        System.out.println("[올바른 형식으로 입력하세요 (y/n).]");
        continue;
      }
      return response;
    }
  }

  private boolean isValidPhoneNumberFormat(String phoneNumber) {
    String phoneNumberPattern = "010-\\d{4}-\\d{4}";
    return !phoneNumber.matches(phoneNumberPattern);
  }

  // Code provided by ChatGPT
  private boolean isDuplicatePhoneNumber(String phoneNumber) {
    LinkedHashMap<Integer, Student> students = this.studentService.getAllStudents();
    return students.values().stream().anyMatch(student -> student.getPhoneNumber().equals(phoneNumber));
  }
}