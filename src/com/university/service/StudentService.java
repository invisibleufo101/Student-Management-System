package com.university.service;

import com.university.model.Student;
import java.util.LinkedHashMap;

public class StudentService {

  private LinkedHashMap<Integer, Student> students = new LinkedHashMap<Integer, Student>();

  /**
   * StudentService의 기본 생성자입니다.
   */
  public StudentService() {}

  /**
   * 새로운 학생을 등록합니다.
   *
   * @param name 학생의 이름.
   * @param studentId 학생의 학번 (키 값으로 등록).
   * @param major 학생의 전공.
   * @param phoneNumber 학생의 전화번호.
   */
  public void registerStudent(String name, int studentId, String major, String phoneNumber) {
    students.put(studentId, new Student(name, studentId, major, phoneNumber));
  }

  /**
   * 프로그램에 등록된 모든 학생들을 반환합니다.
   *
   * @return 학생들의 LinkedHashMap (학번을 키 값으로 등록).
   */
  public LinkedHashMap<Integer, Student> getAllStudents() {
    return students;
  }

  /**
   * 학번으로 학생을 참조하고 반환합니다.
   *
   * @param studentId 참조할 학생의 학번.
   * @return 해당 학생의 객체.
   */
  public Student getStudentById(int studentId) {
    return students.get(studentId);
  }

  /**
   * 학생의 전공과 전화번호를 업데이트합니다.
   *
   * @param studentId 업데이트할 학생의 학번.
   * @param major 업데이트할 학생의 새로운 전공.
   * @param phoneNumber 업데이트할 학생의 새로운 전화번호.
   */
  public void updateStudent(int studentId, String major, String phoneNumber) {
    Student updatedStudent = this.getStudentById(studentId);
    updatedStudent.setMajor(major);
    updatedStudent.setPhoneNumber(phoneNumber);
  }
}
