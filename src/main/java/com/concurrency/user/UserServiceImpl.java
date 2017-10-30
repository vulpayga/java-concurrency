package com.concurrency.user;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UserServiceImpl implements UserService {

  private ExecutorService executor;

  List<UserProfile> userProfiles = Arrays.asList(new UserProfile(1,"Id-1", "course-1"),
                                                 new UserProfile(2,"Id-2", "course-2"),
                                                 new UserProfile(3,"Id-3", "course-3"),
                                                 new UserProfile(4,"Id-4", "course-4"),
                                                 new UserProfile(5,"Id-5", "course-5"));

  public UserServiceImpl() {
    this.executor = Executors.newFixedThreadPool(20);
  }

  @Override
  public UserProfile getProfile(int id) throws InterruptedException {
    TimeUnit.MILLISECONDS.sleep(100);
    return getUserProfile(id);
  }

  @Override
  public CompletableFuture<UserProfile> getProfileCompletableFuture(int id) {
    return CompletableFuture.supplyAsync(() -> getUserProfile(id), executor);
  }

  @Override
  public void stop() {
    executor.shutdown();
  }

  private UserProfile getUserProfile(int id) {
    UserProfile userProfile = userProfiles.stream()
        .filter(p -> p.getId() == id)
        .findFirst()
        .orElse(null);
    return userProfile;
  }

}
