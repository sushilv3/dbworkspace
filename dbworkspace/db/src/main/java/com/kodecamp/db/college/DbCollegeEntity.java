package com.kodecamp.db.college;

import java.util.Collection;

import com.kodecamp.db.student.DbStudentEntity;

/**
 * This interface represents the contracts for the row of college table
 *
 * @author sushil
 */
public interface DbCollegeEntity {

  public boolean delete();

  public boolean rename(final String newName);

  public boolean changeAddress(final String newAddress);

  public String name();

  public String address();

  public Collection<DbStudentEntity> students();
}
