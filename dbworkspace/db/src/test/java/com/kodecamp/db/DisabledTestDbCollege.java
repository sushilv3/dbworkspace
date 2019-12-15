package com.kodecamp.db;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import com.kodecamp.db.college.DbCollegeEntity;
import com.kodecamp.db.college.DbCollegeEntityManagerImpl;
import com.kodecamp.db.college.DbCollegeEntityManger;
import com.kodecamp.db.college.LazyDbCollegeEntityImpl;
import com.kodecamp.db.commons.DbConnection;
import com.kodecamp.db.student.DbStudentEnityManger;
import com.kodecamp.db.student.DbStudentEntityManagerImpl;

public class DisabledTestDbCollege {

  private static DbCollegeEntityManger collegeEm;
  private static DbStudentEnityManger studentEm;
  private static Connection conn;
  private static DbStudentEnityManger dbStudentEm;

  @BeforeAll
  public void setUp() {
    conn = DbConnection.open();
    studentEm = new DbStudentEntityManagerImpl(conn);
    collegeEm = new DbCollegeEntityManagerImpl(conn, studentEm);
    collegeEm.deleteAll();
  }

  @Test
  public void testCreate() {
    final Connection conn = DbConnection.open();

    // Colleges Table
    final DbCollegeEntityManger dbColleges = new DbCollegeEntityManagerImpl(conn);

    final String regNo = "100";
    final String name = "UCER";
    final String address = "NAINI";
    // created row
    Optional<DbCollegeEntity> actualDbCollege = dbColleges.create(regNo, name, address);
    final DbCollegeEntity dbCollege = new LazyDbCollegeEntityImpl(regNo, dbStudentEm, conn);
    Optional<DbCollegeEntity> expectedDbCollege = Optional.of(dbCollege);

    assertTrue(actualDbCollege.equals(expectedDbCollege));

    dbCollege.delete();
    
    DbConnection.close(conn);
  }

  @Test
  public void testFetchAll() {
    Connection conn = DbConnection.open();
    // expected result
    List<CollegeData> rawData = generateTestCollegeData();
    // This data is not of type DbCollege

    DbCollegeEntityManger dbColleges = new DbCollegeEntityManagerImpl(conn);
    final List<Optional<DbCollegeEntity>> existingDbData = new ArrayList<>();
    rawData.forEach(
        (data) -> existingDbData.add(dbColleges.create(data.id, data.name, data.address)));
    
    List<DbCollegeEntity> actualResult = dbColleges.fetchAll();

    List<DbCollegeEntity> existingDataList =
        existingDbData
            .stream()
            .map((Optional<DbCollegeEntity> optColl) -> optColl.orElse(null))
            .collect(Collectors.toList());

    System.out.println("existingDataList : ");
    assertTrue(existingDataList.size() == actualResult.size());
    assertTrue(existingDataList.containsAll(actualResult));

    // delete every record inserted for testing
    actualResult.stream().forEach((dbCollege) -> dbCollege.delete());
  }

  @Test
  public void testGetById() {
    DbCollegeEntityManger dbColleges = new DbCollegeEntityManagerImpl(DbConnection.open());
    final String regNo = "104";
    final String name = "UCER";
    final String address = "NAINI";
    Optional<DbCollegeEntity> exptectedDbCollege = dbColleges.create(regNo, name, address);
    Optional<DbCollegeEntity> actualDbCollege = dbColleges.getBy(regNo);

    assertTrue(exptectedDbCollege.equals(actualDbCollege));

    
  }

  private List<CollegeData> generateTestCollegeData() {
    CollegeData col1 = new CollegeData("101", "Saraswati College", "Maharajganj");
    CollegeData col2 = new CollegeData("102", "City College", "Ashiayana");
    CollegeData col3 = new CollegeData("103", "RRB College", "MP");
    List<CollegeData> exptectedDataList = List.of(col1, col2, col3);
    return exptectedDataList;
  }

  private static void processCollege(DbCollegeEntity college) {
    System.out.println("college created : " + college);
    System.out.println("name : " + college.name());
    System.out.println("address : " + college.address());
  }

  private static void doSomethingIfNotFound() {
    System.out.println("College Could not be created.");
  }
  
  private static class CollegeData {
  	String id;
  	String name;
  	String address;
  	CollegeData(String id, String name, String address) {
  		this.id = id;
  		this.name = name;
  		this.address = address;
  	}
  }
}
