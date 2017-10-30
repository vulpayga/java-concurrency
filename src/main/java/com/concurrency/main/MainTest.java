package com.concurrency.main;

import com.concurrency.grade.GradeService;
import com.concurrency.grade.GradeServiceImpl;
import com.concurrency.grade.StudentGrade;
import com.concurrency.user.UserProfile;
import com.concurrency.user.UserService;
import com.concurrency.user.UserServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainTest {

  List<Integer> ids = Arrays.asList(1,2,3,4,5);

//  List<Integer> ids = Arrays.asList(1,2,3,4,5, 1,2,3,4,5,
//                                    1,2,3,4,5, 1,2,3,4,5,
//                                    1,2,3,4,5, 1,2,3,4,5,
//                                    1,2,3,4,5, 1,2,3,4,5,
//                                    1,2,3,4,5, 1,2,3,4,5);

  UserService userService = new UserServiceImpl();

  GradeService gradeService = new GradeServiceImpl();


  public static void main(String[] args) {
    MainTest mainTest = new MainTest();

    System.out.println("######## START SYNC #######");
    try {
      mainTest.syncResults();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("######## END SYNC #######");


    System.out.println("######## START ASYNC #######");
    try {
      mainTest.asynResults();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("######## END ASYNC #######");
  }

  private void asynResults() throws InterruptedException {
    long startTime = System.nanoTime();

    List<CompletableFuture<StudentGrade>> futures = new ArrayList<>();
    for (Integer id : ids) {
      futures.add(userService.getProfileCompletableFuture(id).thenCompose(profile -> gradeService.getGradeCompletableFuture(profile.getId(), profile.getCourse())));
    }

    CompletableFuture[] cfs = futures.toArray(new CompletableFuture[futures.size()]);

    CompletableFuture<List<StudentGrade>> listCompletableFuture = CompletableFuture.allOf(cfs)
            .thenApply(Void -> futures.stream().map(f -> f.join()).collect(Collectors.toList()));

    try {
      List<StudentGrade> studentGrades = listCompletableFuture.get();
      printResults(startTime, studentGrades);
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    userService.stop();
    gradeService.stop();
  }

  private void printResults(long startTime, List<StudentGrade> studentGrades) {
    long totalTime = TimeUnit.NANOSECONDS.toMillis((System.nanoTime() - startTime));
    System.out.println("Total time to process in milli sec : " + totalTime);
    System.out.println(".........................................................................");
    for (StudentGrade studentGrade : studentGrades) {
      System.out.println(studentGrade.toString());
    }
    System.out.println(".........................................................................");
  }


  private void syncResults() throws InterruptedException  {
    long startTime = System.nanoTime();
    List<StudentGrade> studentGrades = new ArrayList<>();
    for (Integer id : ids) {
      UserProfile profile = userService.getProfile(id);
      if(profile != null) {
        StudentGrade grade = gradeService.getGrade(id, profile.getCourse());
        studentGrades.add(grade);
      }
    }
    printResults(startTime, studentGrades);
  }
}
