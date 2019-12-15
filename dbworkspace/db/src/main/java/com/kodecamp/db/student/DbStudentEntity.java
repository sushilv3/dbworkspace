package com.kodecamp.db.student;

public interface DbStudentEntity {
  public boolean rename(final String newName);

  public boolean changeAddress(final String newAddress);

  public boolean delete();

  public String name();

  public String address();

  public Id id();

  public String rollNo();

  public String collegeCode();

  public String courseCode();

  public int version();

  public void version(int newVersion);
}
