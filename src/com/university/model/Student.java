package com.university.model;

public class Student {

  private final String name;
  private final int studentId;
  private String major;
  private String phoneNumber;

  /**
   * 입력 받는 학생의 이름, 학번, 전공, 전화번호로 새로운 학생의 객체를 생성합니다.
   * @param name 학생의 이름.
   * @param studentId 학생의 학번.
   * @param major 학생의 전공.
   * @param phoneNumber 학생의 전화번호.
   */
  public Student(String name, int studentId, String major, String phoneNumber) {
    this.name = name;
    this.studentId = studentId;
    this.major = major;
    this.phoneNumber = phoneNumber;
  }

  /**
   * 학생의 이름을 반환합니다.
   * @return 학생의 이름.
   */
  public String getName() {
    return this.name;
  }

  /**
   * 학생의 학번을 반환합니다.
   * @return 학생의 학번.
   */
  public int getStudentId() {
    return this.studentId;
  }

  /**
   * 학생의 전공을 반환합니다.
   * @return 학생의 전공.
   */
  public String getMajor() {
    return this.major;
  }

  /**
   * 학생의 전공을 설정합니다.
   * @param major 학생의 새로운 전공.
   */
  public void setMajor(String major) {
    this.major = major;
  }

  /**
   * 학생의 전화번호를 반환합니다.
   * @return 학생의 전화번호.
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * 학생의 전화번호를 설정합니다.
   * @param phoneNumber 학생의 새로운 전화번호.
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
