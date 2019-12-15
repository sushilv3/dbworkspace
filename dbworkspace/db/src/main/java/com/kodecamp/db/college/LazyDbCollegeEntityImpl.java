package com.kodecamp.db.college;

import java.sql.Connection;
import java.util.Collection;
import java.util.Optional;

import com.kodecamp.db.commons.AbstractDbEntity;
import com.kodecamp.db.commons.DbTableNames;
import com.kodecamp.db.student.DbStudentEnityManger;
import com.kodecamp.db.student.DbStudentEntity;

public class LazyDbCollegeEntityImpl extends AbstractDbEntity implements DbCollegeEntity {

  private final String regNo;
  private Collection<DbStudentEntity> students;
  private DbStudentEnityManger studentEntityManager;

  public LazyDbCollegeEntityImpl(
      final String regNo, final DbStudentEnityManger studentEm, final Connection conn) {
    super(conn);
    this.regNo = regNo;
    this.studentEntityManager = studentEm;
  }

  @Override
  public boolean delete() {

    String query =
        String.format("DELETE FROM %s WHERE REG_NO = '%s' ", DbTableNames.COLLEGES, this.regNo);

    return super.executeUpdate(query) > 0 ? true : false;
  }

  @Override
  public boolean rename(String newName) {

    final String query =
        String.format("UPDATE %s SET  NAME = ? WHERE REG_NO = ?", DbTableNames.COLLEGES);
    return super.executeUpdate(query, new Object[] {newName, this.regNo}) > 0 ? true : false;
  }

  @Override
  public boolean changeAddress(String newAddress) {
    String query = String.format("UPDATE %s SET ADDRESS = ? WHERE REG_NO = ?", DbTableNames.COLLEGES);
    return super.executeUpdate(query, new Object[] {newAddress, this.regNo}) > 0 ? true : false;
  }

  @Override
  public String toString() {

    return String.format("[ id : %s ]", this.regNo);
  }

  @Override
  public String name() {
    String query = String.format("SELECT NAME FROM %s WHERE REG_NO = ?", DbTableNames.COLLEGES);
    Optional<Object> valueObj = super.executeQuery(query, new Object[] {this.regNo}).getOne();
    return valueObj.orElse("").toString();
  }

  @Override
  public String address() {
    String query = String.format("SELECT ADDRESS FROM %s WHERE REG_NO = ?", DbTableNames.COLLEGES);
    Optional<Object> valueObj = super.executeQuery(query, new Object[] {this.regNo}).getOne();
    return valueObj.orElse("").toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((regNo == null) ? 0 : regNo.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    LazyDbCollegeEntityImpl other = (LazyDbCollegeEntityImpl) obj;
    if (regNo == null) {
      if (other.regNo != null) return false;
    } else if (!regNo.equals(other.regNo)) return false;
    return true;
  }

  @Override
  public Collection<DbStudentEntity> students() {
    return this.studentEntityManager.fetchAll("WHERE COLLEGE_CODE = ?", new Object[] {this.regNo});
  }
}
