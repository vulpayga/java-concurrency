package com.concurrency.user;

import java.util.concurrent.CompletableFuture;

public interface UserService {

  UserProfile getProfile(int id) throws InterruptedException;

  CompletableFuture<UserProfile> getProfileCompletableFuture(int id);

  void stop();

}
