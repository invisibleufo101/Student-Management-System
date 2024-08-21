package com.university.service;

import com.university.model.Student;
import java.util.LinkedHashMap;

public class StudentService {

  private LinkedHashMap<Integer, Student> students = new LinkedHashMap<Integer, Student>();

  public StudentService() {}

  public void registerStudent(String name, int studentId, String major, String phoneNumber) {
    students.put(studentId, new Student(name, studentId, major, phoneNumber));
  }

  public LinkedHashMap<Integer, Student> getAllStudents() {
    return students;
  }

  public Student getStudentById(int studentId) {
    return students.get(studentId);
  }

  public void updateStudent(int studentId, String major, String phoneNumber) {
    Student updatedStudent = this.getStudentById(studentId);
    updatedStudent.setMajor(major);
    updatedStudent.setPhoneNumber(phoneNumber);
  }
}
