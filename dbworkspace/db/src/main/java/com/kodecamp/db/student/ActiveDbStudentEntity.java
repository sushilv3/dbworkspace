package com.kodecamp.db.student;

public interface ActiveDbStudentEntity extends DbStudentEntity {
  public DbStudentEntity lazyInstance();

  public void refresh();
}
