package com.kodecamp.db.student;

import java.sql.Connection;
import java.util.Optional;

import com.kodecamp.db.commons.AbstractDbEntity;
import com.kodecamp.db.commons.DbTableNames;

class LazyDbStudentEntityImpl extends AbstractDbEntity implements DbStudentEntity {
  private final Id id;
  private int version;

  private final String tableName;

  public LazyDbStudentEntityImpl(
      final Id id, final int version, final Connection conn, final String tableName) {
    super(conn);
    this.id = id;
    this.tableName = tableName;
    this.version = version;
  }

  public LazyDbStudentEntityImpl(final Id id, final int version, final Connection conn) {
    this(id, version, conn, DbTableNames.STUDENTS);
  }

  @Override
  public Id id() {
    return this.id;
  }

  @Override
  public boolean rename(String newName) {
    final String query =
        "UPDATE " + this.tableName + " SET NAME = ?,VERSION = ? WHERE UID = ? AND VERSION = " + this.version;

    return this.executeUpdate(query, new Object[] {newName, this.version + 1, this.id.value()}) > 0
        ? true
        : false;
  }

  @Override
  public boolean changeAddress(String newAddress) {
    final String query =
        String.format(
            "UPDATE %s SET ADDRESS = ?,VERSION= ? WHERE UID = ? AND VERSION = %d",
            this.tableName, this.version);
    return this.executeUpdate(query, new Object[] {newAddress, this.version + 1, id.value()}) > 0
        ? true
        : false;
  }

  @Override
  public String name() {
    final String query = String.format("SELECT NAME FROM %s WHERE UID = ?", this.tableName);
    Optional<Object> valueObj = this.executeQuery(query, new Object[] {this.id.value()}).getOne();

    return valueObj.orElse("").toString();
  }

  @Override
  public String address() {

    String query = String.format("SELECT ADDRESS FROM %s WHERE UID = ? ", this.tableName);
    Optional<Object> valueObj = this.executeQuery(query, new Object[] {this.id.value()}).getOne();

    return valueObj.orElse("").toString();
  }

  @Override
  public boolean delete() {
    final String query =
        String.format(
            "DELETE FROM %s WHERE UID = ? AND VERSION = %d", this.tableName, this.version);
    return this.executeUpdate(query, new Object[] {this.id.value()}) > 0 ? true : false;
  }





  @Override
  public String rollNo() {

    String query = String.format("SELECT ROLL_NO FROM %s WHERE UID = ? ", this.tableName);
    Optional<Object> valueObj = this.executeQuery(query, new Object[] {this.id.value()}).getOne();

    return valueObj.orElse("").toString();
  }

  @Override
  public String collegeCode() {
    String query = String.format("SELECT COLLEGE_CODE FROM %s WHERE UID =?", this.tableName);
    Optional<Object> valueObj = this.executeQuery(query, new Object[] {this.id.value()}).getOne();

    return valueObj.orElse("").toString();
  }

  @Override
  public String courseCode() {
    String query = String.format("SELECT COURSE_CODE FROM %s WHERE UID = ?", this.tableName);
    Optional<Object> valueObj = this.executeQuery(query, new Object[] {this.id.value()}).getOne();

    return valueObj.orElse("").toString();
  }

  @Override
  public int version() {
    return this.version;
  }

  @Override
  public void version(final int newVersion) {
  	 
    this.version = newVersion;
    System.out.println("####### New Version : " + this.version);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    LazyDbStudentEntityImpl other = (LazyDbStudentEntityImpl) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)
        || !this.name().equals(other.name())
        || !this.address().equals(other.address())) return false;
    return true;
  }

  @Override
  public String toString() {
    return "DbStudentImpl [UID = " + id.value() + "]";
  }
}
