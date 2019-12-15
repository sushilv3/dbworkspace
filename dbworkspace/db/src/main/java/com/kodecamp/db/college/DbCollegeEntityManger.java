package com.kodecamp.db.college;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents the contract for collegetable.
 *
 * @author sushil
 */
public interface DbCollegeEntityManger {
  public Optional<DbCollegeEntity> create(final String id, final String name, final String address);

  public List<DbCollegeEntity> fetchAll();

  public Optional<DbCollegeEntity> getBy(final String regNo);

  public boolean deleteBy(final String regNo);

  public int deleteAll();
  //  public DbCollege get(final String query);
}
