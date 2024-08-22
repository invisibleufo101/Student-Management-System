package com.university.ui;

import com.university.exceptions.DuplicatePhoneNumberException;
import com.university.exceptions.DuplicateStudentIdException;
import com.university.exceptions.InvalidMajorException;
import com.university.exceptions.InvalidOptionException;
import com.university.exceptions.InvalidStudentIdException;
import com.university.exceptions.NameFormatException;
import com.university.exceptions.PhoneNumberFormatException;
import com.university.exceptions.StudentIdFormatException;

import com.university.model.Student;
import com.university.service.StudentService;
import com.university.validator.StudentValidator;

import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;

class StudentManagementReader {

  private Scanner scanner = new Scanner(System.in);;
  private StudentService studentService;
  private StudentValidator studentValidator = new StudentValidator();

  /**
   * StudentManagementReader의 기본 생성자입니다.
   *
   * @param studentService StudentService 클래스를 사용하기 위한 객체.
   */
  StudentManagementReader(StudentService studentService) {
    this.studentService = studentService;
  }

  /**
   * 사용자로부터 학생 수를 입력받습니다.
   *
   * 해당 조건의 경우 예외처리를 합니다:
   * - 사용자 입력이 숫자로 변환할 수 없는 경우.
   * - int의 최대값보다 더 클 경우.
   * - 숫자가 0보다 같거나 작을 경우.
   *
   * @return 예외처리된 학생 수.
   */
  int readNumberOfStudents() {
    while (true) {
      try {
        System.out.print("학생 수를 입력하세요: ");
        int numberOfStudents = scanner.nextInt();
        if (numberOfStudents <= 0){
          System.out.println("[학생 수는 0보다 작을 수 없습니다. 다시 입력하세요.]");
          continue;
        }
        return numberOfStudents;
      } catch (InputMismatchException ime) {
        System.out.println("[올바른 형식으로 학생 수를 입력하세요. (숫자 1 - 10자리)]");
        scanner.nextLine();
      }
    }
  }

  /**
   * 사용자로부터 관리번호를 입력받습니다.
   *
   * 해당 조건의 경우 예외처리를 합니다:
   * - 입력이 정수 1 - 5가 아닌 경우.
   * - 숫자로 변환할 수 없는 경우.
   *
   * @return 예외처리된 관리번호.
   */
  int readOption() {
    while (true) {
      try {
        System.out.print("관리 번호를 입력하세요. : ");
        return studentValidator.validateOption(scanner.nextInt());
      } catch (InvalidOptionException ioe) {
        System.out.println(ioe.getMessage());
      } catch (InputMismatchException ime) {
        System.out.println("[숫자 1 - 5를 입력하세요.]");
        scanner.nextLine();
      }
    }
  }

  /**
   * 사용자로부터 입력된 학번을 받습니다.
   *
   * 해당 조건의 경우 예외처리를 합니다:
   * - 숫자로 변환 할 수 없는 경우.
   * - 숫자가 8자리가 아닐 경우
   * - 숫자가 0보다 같거나 작을 경우.
   * - 이미 등록된 학생과 학번이 중복될 경우.
   *
   * @return 예외처리된 학번.
   */
  int readStudentId() {
    while (true) {
      try {
        System.out.print("학\t번 입력 : ");
        LinkedHashMap<Integer, Student>students = this.studentService.getAllStudents();

        int studentId = studentValidator.validateStudentId(scanner.nextInt(), students);
        if (studentId <= 0){
          System.out.println("[학번은 음수가 될 수 없습니다. 다시 입력하세요.]");
          continue;
        }
        return studentId;
      } catch (InputMismatchException ime) {
        System.out.println("[올바른 형식으로 입력하세요 (숫자 8자리). 다시 입력하세요.]");
        scanner.nextLine();
      } catch (StudentIdFormatException | DuplicateStudentIdException customEx) {
        System.out.println(customEx.getMessage());
      }
    }
  }

  /**
   * 사용자로부터 참조할 학생의 학번을 입력받습니다.
   *
   * 해당 조건의 경우 예외처리를 합니다:
   * - 숫자로 변환 할 수 없는 경우.
   * - 숫자가 8자리가 아닐 경우.
   * - 숫자가 0보다 작거나 같을 경우.
   * - 해당 학번을 가진 학생이 없을 경우.
   *
   * @return 예외처리된 참조 학생의 학번.
   */
  int fetchExistingStudentId(){
    while (true){
      try {
        System.out.print("학번을 입력하십시오. : ");
        int studentId = scanner.nextInt();
        Student existingStudent = this.studentService.getStudentById(studentId);

        int existingStudentId = studentValidator.validateStudentId(studentId, existingStudent);
        if (existingStudentId <= 0){
          System.out.println("[학번은 음수가 될 수 없습니다. 다시 입력하세요.]");
          continue;
        }
        return existingStudentId;
      } catch (NumberFormatException nfe){
        System.out.println("[올바른 형식으로 입력하세요 (숫자 8자리). 다시 입력하세요.]");
      } catch (StudentIdFormatException | InvalidStudentIdException customEx){
        System.out.println(customEx.getMessage());
      }
    }
  }

  /**
   * 사용자로부터 입력된 이름을 받습니다.
   *
   * 해당 조건의 경우 예외처리를 합니다:
   * - 이름이 한글이 아닐 경우.
   * - 이름 길이가 2자리 미만 7자리 초과일 경우.
   *
   * @return 예외처리된 학생의 이름.
   */
  String readName(){
    while(true){
      try {
        System.out.print("이\t름 입력 : ");
        return studentValidator.validateName(scanner.next());
      } catch(NameFormatException nfe){
        System.out.println(nfe.getMessage());
        scanner.nextLine();
      }
    }
  }

  /**
   * 사용자로부터 학생의 전공을 입력받습니다.
   *
   * 해당 조건의 경우 예외처리를 합니다:
   * - 학과명이 한글이 아닐 경우.
   * - 학과명이 "학과"로 끝나지 않을 경우.
   *
   * @return 예외처리된 학생의 전공.
   */
  String readMajor(){
    while(true){
      try {
        System.out.print("학\t과 입력 : ");
        return studentValidator.validateMajor(scanner.next());
      } catch(InvalidMajorException inme){
        System.out.println(inme.getMessage());
        scanner.nextLine();
      }
    }
  }

  /**
   * 사용자로부터 학생의 전화번호를 받습니다.
   *
   * 해당 조건의 경우 예외처리를 합니다:
   * - 전화번호가 "010-XXXX-XXXX" 형식을 따르지 않는 경우.
   * - 전화번호가 다른 학생의 전화번호와 중복되는 경우.
   *
   * @return 예외처리된 학생의 전화번호.
   */
  String readPhoneNumber(){
    while(true){
      try {
        System.out.print("전화번호 입력 : ");
        LinkedHashMap<Integer,Student>students = this.studentService.getAllStudents();
        return this.studentValidator.validatePhoneNumber(scanner.next(), students);
      } catch(PhoneNumberFormatException | DuplicatePhoneNumberException customEx) {
        System.out.println(customEx.getMessage());
        scanner.nextLine();
      }
    }
  }

  /**
   * 사용자로부터 업데이트할 학생의 새로운 전화번호를 받습니다.
   *
   * 해당 조건의 경우 예외처리를 합니다:
   * - 전화번호가 "010-XXXX-XXXX" 형식을 따르지 않는 경우.
   * - 새로운 전화번호가 다른 학생들의 기존 전화번호와 중복되는 경우.
   *
   * @param studentId 업데이트할 학생의 학번.
   * @return 예외처리된 해당 학생의 새로운 전화번호.
   */
  String readToUpdatePhoneNumber(int studentId){
    while(true){
      try {
        System.out.print("전화번호 입력 : ");
        String phoneNumber = scanner.next();
        Student updatedStudent = this.studentService.getStudentById(studentId);
        LinkedHashMap<Integer,Student>students = this.studentService.getAllStudents();

        return this.studentValidator.validatePhoneNumber(phoneNumber, updatedStudent, students);
      } catch(PhoneNumberFormatException | DuplicatePhoneNumberException customEx) {
        System.out.println(customEx.getMessage());
      }
    }
  }

  /**
   * 프로그램 종료를 할 때 사용자로부터 정말로 종료할 것인지 확답을 입력받습니다.
   *
   * @return (Y,y,N,n)로 구성된 사용자 입력.
   */
  String readTerminationResponse(){
    while (true) {
      System.out.print("프로그램을 종료하시겠습니까? (y/n) : ");
      String response = scanner.next();
      if (!response.equals("y") && !response.equals("Y") && !response.equals("n") && !response.equals("N")) {
        System.out.println("[y 아니면 n로 입력해주세요.]");
      } else {
        return response;
      }
    }
  }
}
