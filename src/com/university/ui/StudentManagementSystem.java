package com.university.ui;

import com.university.service.StudentService;
import com.university.model.Student;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

public class StudentManagementSystem {

  private StudentService studentService; // Student 객체에 대해 Create Read Update를 처리하는 클래스
  private StudentManagementReader studentManagementReader; // 사용자의 입력과 예외처리를 담당하는 클래스
  private int numberOfStudents;

  public StudentManagementSystem() {
    this.studentService = new StudentService();
    this.studentManagementReader = new StudentManagementReader(this.studentService);
  }

  /**
   * 프로그램 전체 흐름을 조율하기 위한 메소드입니다.
   *
   * 프로그램을 처음 시작할 때 studentManagementReader로 사용자한테 학생 수를 받아온 뒤
   * 프로그램의 전체 흐름을 시작합니다.
   *
   * 그 다음 프로그램이 사용자로부터 관리번호를 입력 받고 각 관리번호마다 맞는 메소드들을 수행합니다.
   * 만약 학생들이 등록되지 않은 상태로 학생 조회, 출력, 참조를 한다면 등록될 때까지 예외처리를 합니다..
   * 이미 학생(들)이 등록된 상태로 다시 등록을 하면 예외처리를 합니다.
   */
  public void run() {
    boolean isRegistered = false;
    numberOfStudents = this.studentManagementReader.readNumberOfStudents();
    while (true) {

      addDelay(800);
      this.printSystemManual();

      int option = this.studentManagementReader.readOption();
      if (option != 1 && option != 5 && !isRegistered) {
        System.out.println("[등록된 학생(들)이 없습니다.]");
        continue;
      }
      if (option == 1 && isRegistered){
        System.out.println("[학생(들)이 이미 등록되었습니다.]");
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
          this.findStudentById();
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

  /**
   * 사용자 프로그램 사용 편의를 위한 helper 메소드.
   *
   * 각 관리번호가 어떤 기능을 하는지 사용자에게 서술함.
   */
  private void printSystemManual() {
    System.out.println("**** 학생 관리 프로그램 ****");
    System.out.println("1. 학생 등록 ");
    System.out.println("2. 전체 출력 ");
    System.out.println("3. 학생 조회");
    System.out.println("4. 정보 수정");
    System.out.println("5. 프로그램 종료");
  }

  /**
   * 학생들을 프로그램에 등록하는 메소드.
   *
   * 각 학생마다 studentManagementReader로 validated된 정보를 받아온 뒤
   * studentService로 학생들을 등록함.
   */
  private void registerStudents() {
    System.out.println("---------------------------------------");
    System.out.println("[학생을 등록합니다.]");

    for (int i = 0; i < numberOfStudents; i++) {
      int studentId = this.studentManagementReader.readStudentId();
      String name = this.studentManagementReader.readName();
      String major = this.studentManagementReader.readMajor();
      String phoneNumber = this.studentManagementReader.readPhoneNumber();

      this.studentService.registerStudent(name, studentId, major, phoneNumber);

      System.out.println("---------------------------------------");
      addDelay(500);
    }
  }

  /**
   * 프로그램에 등록된 모든 학생들을 참조하는 메소드.
   *
   * studentService로 등록된 모든 학생들을 가져온 뒤
   * 각 학생들의 정보를 출력함.
   */
  private void showAllStudents() {
    System.out.println("---------------------------------------");
    System.out.println("====== 전체 학생 출력 ======");

    LinkedHashMap<Integer, Student> students = this.studentService.getAllStudents();
    for (Student student : students.values()) {
      System.out.println("학\t번: " + student.getStudentId());
      System.out.println("이\t름: " + student.getName());
      System.out.println("학\t과: " + student.getMajor());
      System.out.println("연락처: " + student.getPhoneNumber());
      System.out.println("---------------------------------------");

      addDelay(1250);
    }
  }

  /**
   * 특정 학생을 참조하는 메소드.
   *
   * 사용자 입력으로 studentId를 받은 뒤 해당하는 학생을 참조함.
   * studentManagementReader로 사용자로부터 studentId를 받고
   * studentService로 학생을 참조함.
   */
  private void findStudentById() {
    System.out.println("---------------------------------------");
    System.out.println("[학생을 조회합니다.]");
    int validatedStudentId = this.studentManagementReader.fetchExistingStudentId();
    Student student = this.studentService.getStudentById(validatedStudentId);

    System.out.println("학\t번: " + student.getStudentId());
    System.out.println("이\t름: " + student.getName());
    System.out.println("학\t과: " + student.getMajor());
    System.out.println("연락처: " + student.getPhoneNumber());

    addDelay(1000);
  }

  /**
   * 학생 정보를 수정하기 위한 메소드.
   *
   * studentManagementReader로 사용자가 입력하는 studentId가 존재하는지 확인하고
   * studentService로 학생을 참조함.
   * studentManagerReader로 업데이트 하는 정보를 다시 사용자한테 받아온 뒤
   * studentService로 그 학생의 정보를 업데이트함.
   */
  private void updateStudent() {
    System.out.println("---------------------------------------");
    System.out.println("[학생 정보를 수정합니다.]");

    int studentId = this.studentManagementReader.fetchExistingStudentId();
    Student updatedStudent = this.studentService.getStudentById(studentId);

    System.out.println("학\t번 : " + updatedStudent.getStudentId());
    System.out.println("이\t름 : " + updatedStudent.getName());

    String updatedMajor = this.studentManagementReader.readMajor();
    String updatedPhoneNumber = this.studentManagementReader.readToUpdatePhoneNumber(studentId);

    this.studentService.updateStudent(studentId, updatedMajor, updatedPhoneNumber);

    addDelay(800); // 가독성을 위한 코드
  }

  /**
   * 프로그램 종료를 위한 메소드.
   *
   * studentManagementReader에서 유저 입력을 받아옴.
   * 사용자 입력이 "y"나 "Y"이면 프로그램을 종료함.
   */
  private void terminateProcess() {
    System.out.println("---------------------------------------");
    String response = this.studentManagementReader.readTerminationResponse();
    if (response.equals("y") || response.equals("Y")) {
      System.exit(0);
    }
  }

  /**
   * 화면에 나오는 정보의 가독성을 높이기 위해 만든 메소드.
   *
   * @param milliSeconds 딜레이를 정하는 시간
   */
  private void addDelay(int milliSeconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliSeconds);
    } catch (InterruptedException ie) {
      System.out.println("[프로그램을 종료합니다.]");
    }
  }
}
