package com.kodecamp.db.student;

import java.util.Collection;
import java.util.Optional;

public interface DbStudentEnityManger {
  //  public Connection dbConnection(final Connection conn);

  /**
   * @param id
   * @param name
   * @param address
   * @param rollNo
   * @param collegeCode
   * @param courseCode
   * @return
   */
  public Optional<DbStudentEntity> create(
      final Id id,
      final String name,
      final String address,
      final String rollNo,
      final String collegeCode,
      final String courseCode);

  /** @return */
  public Collection<DbStudentEntity> fetchAll();


  /**
   * @param id
   * @return
   */
  public Optional<DbStudentEntity> getBy(final String id);

  /**
   * @param id
   * @return
   */
  public boolean deleteBy(final String id, final int currentVersion);

  public boolean deleteBy(final String id);

  public int deleteByWhere(final String whereClause, final Object[] values);

  /** @return */
  public int deleteAll();

  /**
   * This method will be used to fetch the students with the specified parameterized where clause
   * and values.
   *
   * @param whereClause e.g : where courseCode = ? and collegeCode = ?
   * @param values e.g new Object[]{"CSC","UNITED001"}
   * @return
   */
  public Collection<DbStudentEntity> fetchAll(final String whereClause, final Object[] values);
}
