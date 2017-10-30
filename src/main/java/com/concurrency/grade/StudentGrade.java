package com.concurrency.grade;

public class StudentGrade {

  private String course;
  private int studentId;
  private String grade;
  private double score;

  public StudentGrade(String course, int studentId, String grade, double score) {
    this.course = course;
    this.studentId = studentId;
    this.grade = grade;
    this.score = score;
  }

  public String getCourse() {
    return course;
  }

  public int getStudentId() {
    return studentId;
  }

  public String getGrade() {
    return grade;
  }

  public double getScore() {
    return score;
  }

  @Override
  public String toString() {
    return "StudentGrade{" +
           "course='" + course + '\'' +
           ", studentId=" + studentId +
           ", grade='" + grade + '\'' +
           ", score=" + score +
           '}';
  }
}
