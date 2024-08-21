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
import java.util.Set;

class StudentManagementReader {

  private Scanner scanner = new Scanner(System.in);;
  private StudentService studentService;
  private StudentValidator studentValidator = new StudentValidator();

  StudentManagementReader(StudentService studentService) {
    this.studentService = studentService;
  }

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

  int readNumberOfStudents() {
    while (true) {
      try {
        System.out.print("학생 수를 입력하세요: ");
        return scanner.nextInt();
      } catch (InputMismatchException ime) {
        System.out.println("[숫자로 학생 수를 입력하세요.]");
        scanner.nextLine();
      }
    }
  }

  int readStudentId() {
    while (true) {
      try {
        System.out.print("학\t번 입력 : ");
        LinkedHashMap<Integer, Student>students = this.studentService.getAllStudents();
        return studentValidator.validateStudentId(scanner.nextInt(), students);
      } catch (NumberFormatException nfe) {
        System.out.println("[올바른 형식으로 입력하세요 (숫자 8자리). 다시 입력하세요.]");
      } catch (StudentIdFormatException | DuplicateStudentIdException customEx) {
        System.out.println(customEx.getMessage());
      }
    }
  }

  int fetchExistingStudentId(){
    while (true){
      try {
        System.out.print("학번을 입력하십시오. : ");
        int studentId = scanner.nextInt();
        Student existingStudent = this.studentService.getStudentById(studentId);
        return studentValidator.validateStudentId(studentId, existingStudent);
      } catch (NumberFormatException nfe){
        System.out.println("[올바른 형식으로 입력하세요 (숫자 8자리). 다시 입력하세요.]");
      } catch (StudentIdFormatException | InvalidStudentIdException customEx){
        System.out.println(customEx.getMessage());
      }
    }
  }

  String readName(){
    while(true){
      try {
        System.out.print("이\t름 입력 : ");
        return studentValidator.validateName(scanner.next());
      } catch(NameFormatException nfe){
        System.out.println(nfe.getMessage());
      }
    }
  }

  String readMajor(){
    while(true){
      try {
        System.out.print("학\t과 입력 : ");
        return studentValidator.validateMajor(scanner.next());
      } catch(InvalidMajorException inme){
        System.out.println(inme.getMessage());
      }
    }
  }

  String readPhoneNumber(){
    while(true){
      try {
        System.out.print("전화번호 입력 : ");
        LinkedHashMap<Integer,Student>students = this.studentService.getAllStudents();
        return this.studentValidator.validatePhoneNumber(scanner.next(), students);
      } catch(PhoneNumberFormatException | DuplicatePhoneNumberException customEx) {
        System.out.println(customEx.getMessage());
      }
    }
  }

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

  String readTerminationResponse(){
    return scanner.next();
  }
}
