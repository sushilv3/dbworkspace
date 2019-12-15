package com.kodecamp.db.college;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.kodecamp.db.commons.DbAware;
import com.kodecamp.db.commons.DbTableNames;
import com.kodecamp.db.student.DbStudentEnityManger;

/**
 * This class depends upon <code>DbStudentEntityManager</code> to fetch the child from student
 * table.
 *
 * @author sushil
 */
public class DbCollegeEntityManagerImpl extends DbAware<DbCollegeEntity>
    implements DbCollegeEntityManger {

  private Connection conn;
  //  private String tableName;
  private final DbStudentEnityManger studentEntityManager;

  public DbCollegeEntityManagerImpl(final Connection conn, final DbStudentEnityManger stuEm) {
    super(conn);
    if (stuEm == null) {
      throw new IllegalStateException(
          "Not a valid entity : Dependency Missing : DbStudentEnityManger");
    }
    this.conn = conn;
    //    this.tableName = tableName;
    this.studentEntityManager = stuEm;
  }

  public DbCollegeEntityManagerImpl(final Connection conn) {
    this(conn, null);
  }


  @Override
  public Optional<DbCollegeEntity> create(String reg_no, String name, String address) {
    String query =
        String.format(
            "INSERT INTO %s ( REG_NO, NAME , ADDRESS) VALUES (?,?,?)", DbTableNames.COLLEGES);
    int insertCount = super.executeUpdate(query, new Object[] {reg_no, name, address});
    return insertCount > 0
        ? Optional.of(
            new ActiveDbCollegeEntityImpl(
                new LazyDbCollegeEntityImpl(reg_no, this.studentEntityManager, conn),
                name,
                address))
        : Optional.empty();
  }

  @Override
  public List<DbCollegeEntity> fetchAll() {
    String query = String.format("SELECT REG_NO FROM %s", DbTableNames.COLLEGES);
    List<DbCollegeEntity> colleges = super.executeQuery(query).getMany();
    return colleges;
  }

  @Override
  public Optional<DbCollegeEntity> getBy(String regNo) {
    String query = String.format("SELECT REG_NO FROM %s where REG_NO = ?", DbTableNames.COLLEGES);
    Optional<DbCollegeEntity> college = super.executeQuery(query, new Object[] {regNo}).getOne();
    return college;
  }


  @Override
  public boolean deleteBy(String regNo) {

    return this.studentEntityManager.deleteByWhere("WHERE COLLEGE_CODE = ?", new Object[] {regNo})
            >= 0
        ? (this.executeUpdate(
                    String.format("DELETE FROM %s WHERE REG_NO = ?", DbTableNames.COLLEGES),
                    new Object[] {regNo})
                > 0
            ? true
            : false)
        : false;
  }

  /** User fetchAll and deleteBy to delete all the colleges and associated students; */
  @Deprecated
  @Override
  public int deleteAll() {
    return this.executeUpdate(String.format("DELETE FROM %s", DbTableNames.COLLEGES));
  }

  @Override
  protected DbCollegeEntity mapToObject(Map<String, Object> row) {
    final String name = (String) row.get("NAME");
    final String address = (String) row.get("ADDRESS");
    return new ActiveDbCollegeEntityImpl(
        new LazyDbCollegeEntityImpl((String) row.get("REG_NO"), this.studentEntityManager, conn),
        name,
        address);
  }
}
