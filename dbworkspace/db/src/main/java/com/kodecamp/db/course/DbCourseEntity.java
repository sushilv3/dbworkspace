package com.kodecamp.db.course;

public interface DbCourseEntity {

  public boolean rename(final String newName);

  public boolean updateSeats(final int newValue);

  public String name();

  public int seats();

  public int availbaleSeats();
}
