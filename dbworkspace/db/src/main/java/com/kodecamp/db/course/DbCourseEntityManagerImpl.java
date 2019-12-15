package com.kodecamp.db.course;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.kodecamp.db.commons.DbAware;
import com.kodecamp.db.commons.DbTableNames;

public class DbCourseEntityManagerImpl extends DbAware<DbCourseEntity>
    implements DbCourseEntityManager {
  private final Connection conn;
  private final String tableName;

  public DbCourseEntityManagerImpl(final Connection conn, final String tableName) {
    super(conn);
    this.conn = conn;
    this.tableName = tableName;
  }

  public DbCourseEntityManagerImpl(final Connection conn) {
    this(conn, DbTableNames.COURSES);
  }

  @Override
  public Optional<DbCourseEntity> create(
      String name, String courseCode, int seats, int availableSeats) {
    final String query =
        String.format(
            "INSERT INTO %s (NAME,COURSE_CODE,SEATS,AVAILABLE_SEATS) VALUES(?,?,?,?)",
            this.tableName);
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!" + query);
    int insertCount =
        super.executeUpdate(query, new Object[] {name, courseCode, seats, availableSeats});
    System.out.println("$$$$$$$$$$$$$$$$$$$$" + insertCount);
    return insertCount != 0
        ? Optional.ofNullable(
            new ActiveDbCourseEntityImpl(
                new LazyDbCourseEntityImpl(conn, courseCode), name, seats, availableSeats))
        : Optional.empty();
  }

  @Override
  public List<DbCourseEntity> fetchAll() {
    String query = String.format("SELECT COURSE_CODE FROM %s", this.tableName);
    List<DbCourseEntity> courses = super.executeQuery(query).getMany();
    return courses;
  }

  @Override
  public Optional<DbCourseEntity> getBy(String courseCode) {
    String query =
        String.format("SELECT COURSE_CODE FROM %s WHERE COURSE_CODE = ?", this.tableName);
    Optional<DbCourseEntity> course = super.executeQuery(query, new Object[] {courseCode}).getOne();
    return course;
  }

  @Override
  public boolean deleteBy(String courseCode) {
    String query = String.format("DELETE FROM %s WHERE COURSE_CODE = ?", this.tableName);

    return super.executeUpdate(query, new Object[] {courseCode}) > 0 ? true : false;
  }

  @Override
  public int deleteAll() {
    return super.executeUpdate(String.format("DELETE FROM %s", this.tableName));
  }

  @Override
  protected DbCourseEntity mapToObject(Map<String, Object> row) {
    String name = (String) row.get("NAME");
    int seats = (int) row.get("SEATS");
    int availableSeats = (int) row.get("AVAILABLE_SEATS");

    return new ActiveDbCourseEntityImpl(
        new LazyDbCourseEntityImpl(conn, (String) row.get("COURSE_CODE")),
        name,
        seats,
        availableSeats);
  }


}
