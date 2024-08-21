package com.university.model;

public class Student {

  private final String name;
  private final int studentId;
  private String major;
  private String phoneNumber;

  public Student(String name, int studentId, String major, String phoneNumber) {
    this.name = name;
    this.studentId = studentId;
    this.major = major;
    this.phoneNumber = phoneNumber;
  }

  public String getName() {
    return this.name;
  }

  public int getStudentId() {
    return this.studentId;
  }

  public String getMajor() {
    return this.major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return this.getName() + " " + this.getStudentId() + " " +
      this.getMajor() + " " + this.getPhoneNumber() + " " +
      this.getPhoneNumber();
  }
}
