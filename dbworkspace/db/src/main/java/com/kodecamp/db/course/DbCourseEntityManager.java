package com.kodecamp.db.course;

import java.util.List;
import java.util.Optional;

public interface DbCourseEntityManager {
  public Optional<DbCourseEntity> create(
      final String name, final String courseCode, final int seats, final int AvailableSeats);

  public List<DbCourseEntity> fetchAll();

  public Optional<DbCourseEntity> getBy(final String courseCode);

  public boolean deleteBy(final String courseCode);

  public int deleteAll();
}
