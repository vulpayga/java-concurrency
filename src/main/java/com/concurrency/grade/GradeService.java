package com.concurrency.grade;

import java.util.concurrent.CompletableFuture;

public interface GradeService {

  StudentGrade getGrade(int studentId, String course) throws InterruptedException;

  CompletableFuture<StudentGrade> getGradeCompletableFuture(int studentId, String course);

  void stop();

}
