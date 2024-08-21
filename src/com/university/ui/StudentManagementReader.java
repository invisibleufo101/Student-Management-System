package com.university.ui;

import com.university.exceptions.DuplicateStudentIdException;
import com.university.exceptions.InvalidOptionException;
import com.university.exceptions.StudentIdFormatException;
import com.university.model.Student;
import com.university.service.StudentService;
import com.university.validator.StudentValidator;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Scanner;
public class StudentManagementReader {

  private Scanner scanner;
  private StudentValidator studentValidator;
  private StudentService studentService;
  private boolean isRegistered = false;

  public StudentManagementReader(StudentService studentService) {
    this.scanner = new Scanner(System.in);
    this.studentValidator = new StudentValidator();
    this.studentService = studentService;
  }

  public int readOption() {
    while (true) {
      try {
        System.out.print("관리 번호를 입력하세요. : ");
        return this.studentValidator.validateOption(this.scanner.nextInt());
      } catch (InvalidOptionException ioe) {
        System.out.println(ioe.getMessage());
      } catch (InputMismatchException ime) {
        System.out.println("[숫자 1 - 5를 입력하세요.]");
        this.scanner.nextLine();
      }
    }
  }

  public int readNumberOfStudents() {
    while (true) {
      try {
        System.out.print("학생 수를 입력하세요: ");
        return this.scanner.nextInt();
      } catch (InputMismatchException ime) {
        System.out.println("[숫자로 학생 수를 입력하세요.]");
        this.scanner.nextLine();
      }
    }
  }

  public int readStudentId() {
    while (true) {
      try {
        System.out.print("학\t번 입력 : ");
        LinkedHashMap<Integer, Student>students = this.studentService.getAllStudents();
        return this.studentValidator.validateStudentId(this.scanner.nextInt(), students);
      } catch (NumberFormatException nfe) {
        System.out.println("[올바른 형식으로 입력하세요 (숫자 8자리). 다시 입력하세요.]");
      } catch (StudentIdFormatException | DuplicateStudentIdException customEx) {
        System.out.println(customEx.getMessage());
      }
    }
  }


//public int readStudentId() {
//  while (true) {
//    try {
//      System.out.print("학\t번 입력 : ");
//
//      String input = this.scanner.next();
//      if (input.length() != 8) {
//        throw new StudentIdFormatException();
//      }
//      int studentId = Integer.parseInt(input);
//      if (!Objects.isNull(this.studentService.getStudentById(studentId))) {
//        throw new DuplicateStudentIdException();
//      }
//      return studentId;
//    } catch (NumberFormatException nfe) {
//      System.out.println("[올바른 형식으로 입력하세요 (숫자 8자리). 다시 입력하세요.]");
//    } catch (StudentIdFormatException | DuplicateStudentIdException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//}

}

