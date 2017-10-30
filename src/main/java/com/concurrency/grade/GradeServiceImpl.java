package com.concurrency.grade;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GradeServiceImpl implements GradeService {

  ExecutorService executor;

  List<StudentGrade> studentGrades = Arrays.asList(new StudentGrade("course-1", 1, "A", 91),
                                                   new StudentGrade("course-2", 2, "A", 92),
                                                   new StudentGrade("course-3", 3, "A", 93),
                                                   new StudentGrade("course-4", 4, "A", 94),
                                                   new StudentGrade("course-5", 5, "A", 95),
                                                   new StudentGrade("course-2", 1, "C", 20),
                                                   new StudentGrade("course-3", 1, "D", 30));

  public GradeServiceImpl() {
    executor = Executors.newFixedThreadPool(20);
  }

  @Override
  public StudentGrade getGrade(int studentId, String course) throws InterruptedException {
    TimeUnit.MILLISECONDS.sleep(100);
    StudentGrade studentGrade = getStudentGrade(studentId, course);
    return studentGrade;
  }

  @Override
  public CompletableFuture<StudentGrade> getGradeCompletableFuture(int studentId, String course)  {
    try {
      TimeUnit.MILLISECONDS.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return CompletableFuture.supplyAsync(() -> getStudentGrade(studentId, course), executor);
  }

  private StudentGrade getStudentGrade(int studentId, String course) {
    StudentGrade studentGrade = studentGrades.stream()
        .filter(g -> g.getCourse().contentEquals(course) && g.getStudentId() == studentId)
        .findFirst().orElse(null);
    return studentGrade;
  }

  @Override
  public void stop() {
    executor.shutdown();
  }

}
