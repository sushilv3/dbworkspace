package com.kodecamp.db.student;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.kodecamp.db.commons.DbAware;
import com.kodecamp.db.commons.DbTableNames;
import com.kodecamp.db.commons.ReadOnlyEntityManager;

public class DbStudentEntityManagerImpl extends DbAware<DbStudentEntity>
    implements DbStudentEnityManger {
  private final String tableName;
  private final Connection conn;
  private ReadOnlyEntityManager<DbStudentEntity> rEm = new ReadOnlyDbStudentEntityManagerImpl(this);

  /**
   * Constructor
   *
   * @param conn
   * @param tableName
   */
  public DbStudentEntityManagerImpl(final Connection conn, final String tableName) {
    super(conn);
    this.conn = conn;
    this.tableName = tableName.toUpperCase();
  }

  /**
   * Constructor
   *
   * @param conn
   */
  public DbStudentEntityManagerImpl(final Connection conn) {
    this(conn, DbTableNames.STUDENTS);
  }

  @Override
  public Optional<DbStudentEntity> create(
      final Id uId,
      final String name,
      final String address,
      final String rollNo,
      final String collegeCode,
      final String courseCode) {

    String query =
        String.format(
            "INSERT INTO %s ( NAME , ADDRESS , UID , ROLL_NO , COLLEGE_CODE , COURSE_CODE,VERSION) VALUES (?,?,?,?,?,?,?)",
            this.tableName);
    final int insertionCount =
        super.executeUpdate(
            query, new Object[] {name, address, uId.value(), rollNo, collegeCode, courseCode, 0});
    DbStudentEntity lazyInstance = new LazyDbStudentEntityImpl(uId, 0, this.conn, this.tableName);

    DbStudentEntity createdStudent =
        insertionCount > 0
            ? new ActiveDbStudentEntityImpl(
                rEm, lazyInstance, name, address, rollNo, collegeCode, courseCode)
            : null;
    return Optional.ofNullable(createdStudent);
  }

  @Override
  public Collection<DbStudentEntity> fetchAll() {
    final String query =
        String.format(
            "SELECT UID,NAME,ADDRESS,ROLL_NO,COLLEGE_CODE,COURSE_CODE,VERSION FROM %s",
            this.tableName);
    List<DbStudentEntity> rows = super.executeQuery(query).getMany();
    return rows;
  }

  @Override
  public Optional<DbStudentEntity> getBy(final String id) {
    final String query =
        String.format(
            "SELECT UID,NAME,ADDRESS,ROLL_NO,COLLEGE_CODE,COURSE_CODE,VERSION FROM %s WHERE UID = ?",
            this.tableName);
    //    System.out.println("????????? " + query);
    Optional<DbStudentEntity> dbStudent = super.executeQuery(query, new Object[] {id}).getOne();
    return dbStudent;
  }

  /** This method will delete the record having the specified <b>ID</b> */
  @Override
  public boolean deleteBy(final String id, final int currentVersion) {
    return this.executeUpdate(
                String.format(
                    "DELETE FROM %s WHERE UID = '%s' and version = %d",
                    this.tableName, id, currentVersion))
            > 0
        ? true
        : false;
  }

  @Override
  public boolean deleteBy(final String id) {
    return this.executeUpdate(
                String.format(
                    "DELETE FROM %s WHERE UID = '%s' and version = 0", this.tableName, id))
            > 0
        ? true
        : false;
  }

  @Override
  public int deleteAll() {
    return this.executeUpdate(String.format("DELETE FROM %s", this.tableName));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DbStudentEntityManagerImpl other = (DbStudentEntityManagerImpl) obj;
    if (tableName == null) {
      if (other.tableName != null) return false;
    } else if (!tableName.equals(other.tableName)) return false;
    return true;
  }

  @Override
  protected DbStudentEntity mapToObject(Map<String, Object> row) {
    final String id = (String) row.get("UID");
    final String name = (String) row.get("NAME");
    final String address = (String) row.get("ADDRESS");
    final String rollNo = (String) row.get("ROLL_NO");
    final String collegeCode = (String) row.get("COLLEGE_CODE");
    final String courseCode = (String) row.get("COURSE_CODE");
    final int version = (Integer) row.get("VERSION");
    DbStudentEntity lazyInstance =
        new LazyDbStudentEntityImpl(new StudentId(id), version, conn, this.tableName);
    return new ActiveDbStudentEntityImpl(
        this.rEm, lazyInstance, name, address, rollNo, collegeCode, courseCode);
  }

  
  @Override
  public Collection<DbStudentEntity> fetchAll(final String whereClause,final Object[] values) {
    final String query =
        String.format(
            "SELECT UID,NAME,ADDRESS,ROLL_NO,COLLEGE_CODE,COURSE_CODE,VERSION FROM %s %s",
            this.tableName, whereClause);
    List<DbStudentEntity> rows = super.executeQuery(query, values).getMany();
    return rows;
  }

  @Override
  public int deleteByWhere(String whereClause, final Object[] values) {
    return this.executeUpdate(String.format("DELETE FROM %s %s", this.tableName, whereClause));
  }
}
