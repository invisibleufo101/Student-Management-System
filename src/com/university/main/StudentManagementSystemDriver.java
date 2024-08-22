package com.university.main;

import com.university.ui.StudentManagementSystem;

public class StudentManagementSystemDriver {

  /**
   * StudentManagementSystem을 실행하는 Driver 클래스
   * @void StudentManagementSystem의 객체를 만들고 run() 메소드로 실행을 시작합니다.
   */
  public static void main(String[] args) {
    StudentManagementSystem studentManagementSystem = new StudentManagementSystem();
    studentManagementSystem.run();
  }
}
