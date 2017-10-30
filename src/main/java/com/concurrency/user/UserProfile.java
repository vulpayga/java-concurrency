package com.concurrency.user;

public class UserProfile {

  private int id;
  private String name;
  private String course;

  public UserProfile(int id, String name, String course) {
    this.id = id;
    this.name = name;
    this.course = course;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCourse() {
    return course;
  }

  @Override
  public String toString() {
    return "UserProfile{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", course='" + course + '\'' +
           '}';
  }

}
