package com.university.ui;

import com.university.service.StudentService;
import com.university.model.Student;
import com.university.ui.StudentManagementReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class StudentManagementSystem {

  private StudentService studentService;
  private StudentManagementReader studentManagementReader;
  private int numberOfStudents;

  public StudentManagementSystem() {
    this.studentService = new StudentService();
    this.studentManagementReader = new StudentManagementReader(this.studentService);
  }

  public void run() {
    numberOfStudents = this.studentManagementReader.readNumberOfStudents();

    boolean isRegistered = false;
    while (true) {
      try {
        TimeUnit.MILLISECONDS.sleep(1000);
      } catch (InterruptedException ie){
        System.out.println("[프로그램을 종료합니다.]");
      }

      this.printSystemManual();

      int option = this.studentManagementReader.readOption();
      if (option != 1 && option != 5 && !isRegistered) {
        System.out.println("[등록된 학생(들)이 없습니다.]");
        continue;
      }

      switch (option) {
        case 1:
          this.registerStudents();
          isRegistered = true;
          break;

        case 2:
          this.showAllStudents();
          break;

        case 3:
          this.getStudentById();
          break;

        case 4:
          this.updateStudent();
          break;

        case 5:
          this.terminateProcess();
          break;
      }
    }
  }

  private void printSystemManual() {
    System.out.println("**** 학생 관리 프로그램 ****");
    System.out.println("1. 학생 등록 ");
    System.out.println("2. 전체 출력 ");
    System.out.println("3. 학생 조회");
    System.out.println("4. 정보 수정");
    System.out.println("5. 프로그램 종료");
  }

  private void registerStudents() {
    System.out.println("---------------------------------------");
    System.out.println("[학생을 등록합니다.]");

    for (int i = 0; i < numberOfStudents; i++) {
      int studentId = this.studentManagementReader.readStudentId();
      String name = this.studentManagementReader.readName();
      String major = this.studentManagementReader.readMajor();
      String phoneNumber = this.studentManagementReader.readPhoneNumber();

      System.out.println("---------------------------------------");

      this.studentService.registerStudent(name, studentId, major, phoneNumber);
    }
  }

  private void showAllStudents() {
    System.out.println("---------------------------------------");
    System.out.println("====== 전체 학생 출력 ======");

    LinkedHashMap<Integer, Student> students = this.studentService.getAllStudents();
    for (Map.Entry<Integer, Student> studentEntry : students.entrySet()) {
      System.out.println("학\t번: " + studentEntry.getKey());
      System.out.println("이\t름: " + studentEntry.getValue().getName());
      System.out.println("학\t과: " + studentEntry.getValue().getMajor());
      System.out.println("연락처: " + studentEntry.getValue().getPhoneNumber());
      System.out.println("---------------------------------------");

      try {
        TimeUnit.MILLISECONDS.sleep(1000);
      } catch (InterruptedException ie){
        System.out.println("[프로그램을 종료합니다.]");
      }
    }
  }

  private void getStudentById() {
    System.out.println("---------------------------------------");
    System.out.println("[학생을 조회합니다.]");
    int validatedStudentId = this.studentManagementReader.readStudentId();
    Student student = this.studentService.getStudentById(validatedStudentId);

    System.out.println("학\t번: " + student.getStudentId());
    System.out.println("이\t름: " + student.getName());
    System.out.println("학\t과: " + student.getMajor());
    System.out.println("연락처: " + student.getPhoneNumber());
  }

  private void updateStudent() {
    System.out.println("---------------------------------------");
    System.out.println("[학생 정보를 수정합니다.]");

    int studentId = this.studentManagementReader.readStudentId();
    Student student = this.studentService.getStudentById(studentId);

    System.out.println("학\t번 : " + student.getStudentId());
    System.out.println("이\t름 : " + student.getName());

    String updatedMajor = this.studentManagementReader.readMajor();
    String updatedPhoneNumber = this.studentManagementReader.readPhoneNumber();

    this.studentService.updateStudent(studentId, updatedMajor, updatedPhoneNumber);
  }

  private void terminateProcess() {
    System.out.println("---------------------------------------");

    while(true){
      String response = this.studentManagementReader.readTerminationResponse();
      if (response.equals("y")) System.exit(0);
      if (response.equals("n")) break;
    }
  }
}
