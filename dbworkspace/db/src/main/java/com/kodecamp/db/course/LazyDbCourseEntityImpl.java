package com.kodecamp.db.course;

import java.sql.Connection;
import java.util.Optional;

import com.kodecamp.db.commons.AbstractDbEntity;
import com.kodecamp.db.commons.DbTableNames;

public class LazyDbCourseEntityImpl extends AbstractDbEntity implements DbCourseEntity {

  private final String courseCode;
  private final Connection conn;
  private final String tableName = DbTableNames.COURSES;

  public LazyDbCourseEntityImpl(final Connection conn, final String courseCode) {
    super(conn);
    this.conn = conn;
    this.courseCode = courseCode;
   }
  
  @Override
  public boolean rename(String newName) {
    return super.executeUpdate(
                String.format("UPDATE %s SET NAME = ? WHERE COURSE_CODE = ?", this.tableName),
                new Object[] {newName, this.courseCode})
            > 0
        ? true
        : false;
  }

  @Override
  public boolean updateSeats(int newValue) {
    return super.executeUpdate(
                String.format("UPDATE %s SET SEATS = ? WHERE COURSE_CODE = ?", this.tableName),
                new Object[] {newValue, this.courseCode})
            > 0
        ? true
        : false;
  }

  @Override
  public String name() {
 
    String query = String.format("SELECT NAME FROM %s WHERE COURSE_CODE = ?", this.tableName);

    Optional<Object> courseObj = super.executeQuery(query, new Object[] {this.courseCode}).getOne();
    return (String) courseObj.orElse("");
  }

 

  @Override
  public int seats() {
    String query = String.format("SELECT SEATS FROM %s WHERE COURSE_CODE = ?", this.tableName);

    Optional<Object> courseObj = super.executeQuery(query, new Object[] {this.courseCode}).getOne();
    return (Integer) courseObj.orElse("");
  }

  @Override
  public int availbaleSeats() {
    String query =
        String.format("SELECT AVAILABLE_SEATS FROM %s WHERE COURSE_CODE = ?", this.tableName);

    Optional<Object> courseObj = super.executeQuery(query, new Object[] {this.courseCode}).getOne();
    return (Integer) courseObj.orElse("");
  }

  @Override
  public String toString() {
    return "DbCourseEntityImpl [courseCode=" + courseCode + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
    return result;
  }
//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj) return true;
//    if (obj == null) return false;
//    if (getClass() != obj.getClass()) return false;
//    DbStudentEntityImpl other = (DbStudentEntityImpl) obj;
//    if (id == null) {
//      if (other.id != null) return false;
//    } else if (!id.equals(other.id)
//        || !this.name().equals(other.name())
//        || !this.address().equals(other.address())) return false;
//    return true;
//  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    LazyDbCourseEntityImpl other = (LazyDbCourseEntityImpl) obj;
    if (courseCode == null) {
      if (other.courseCode != null) return false;
    } else if (!courseCode.equals(other.courseCode) || !this.name().equals(other.name()))
      return false;
    return true;
  }
}
