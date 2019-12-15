package com.kodecamp.db;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kodecamp.db.commons.DbConnection;
import com.kodecamp.db.student.ActiveDbStudentEntity;
import com.kodecamp.db.student.DbStudentEnityManger;
import com.kodecamp.db.student.DbStudentEntity;
import com.kodecamp.db.student.DbStudentEntityManagerImpl;
import com.kodecamp.db.student.StudentId;


public class DisabledTestDbStudents {
  private static Connection conn;
  private static List<DbStudentEntity> existingDbStudents;
  private static DbStudentEntity existingDbStudent;
  private static DbStudentEnityManger dbStudentsObj;
  private static String id = "At001";

  @BeforeAll
  static void setUp() {
    conn = DbConnection.open();
    dbStudentsObj = new DbStudentEntityManagerImpl(conn);
    // TODO : backup all data into another table
    // TODO : clean the table ( Delete all data )
    dbStudentsObj.deleteAll();
  }

  @BeforeEach
  private void createDataForEachTest() {

    final DbStudentEntity emptyDbStudent = null;

    existingDbStudents =
        List.of(
            dbStudentsObj
                .create(new StudentId("At001"), "Atul", "Ruchikhand", "Roll001", "Bab001", "BC12")
                .orElse(emptyDbStudent),
            dbStudentsObj
                .create(new StudentId("Di001"), "Dinesh", "Sector D", "Di012", "Bab001", "MT14")
                .orElse(emptyDbStudent),
            dbStudentsObj
                .create(new StudentId("Di002"), "Dinesh2", "Sector D2", "Di0122", "Uni001", "BB11")
                .orElse(emptyDbStudent),
            dbStudentsObj
                .create(new StudentId("Di003"), "Dinesh3", "Sector D3", "Di0123", "Uni001", "MB15")
                .orElse(emptyDbStudent));

    existingDbStudent = dbStudentsObj.getBy("At001").orElse(null);
    ;
  }

  @AfterEach
  private void cleanDataAfterEach() {
    dbStudentsObj.deleteAll();
  }

  @AfterAll
  static void cleanUp() {
    DbConnection.close(conn);
  }

  @Test
  public void testSample() {
    System.out.println("Hell Test");
  }

  
  @DisplayName("Create New Student")
  @Test
  public void testCreateStudent() {
    DbStudentEntity expectedDbStudentsObj = existingDbStudent;
    int index = existingDbStudents.indexOf(expectedDbStudentsObj);
    assertTrue(index >= 0);
  }

  
  @DisplayName("Get Student By Id")
  @Test
  public void testGetBy() {
    DbStudentEntity actualStudentObj = dbStudentsObj.getBy(id).orElse(null);
    System.out.println("ACTUAL OBJ : " + actualStudentObj);

    DbStudentEntity excpectedStudentObj = existingDbStudent;
    System.out.println("EXPECTED OBJ : " + excpectedStudentObj);
    assertTrue(actualStudentObj.equals(excpectedStudentObj));
    System.out.println("=========== NO PROBLEM TILL HERE");
  }

  
  @DisplayName("Fetch All Students")
  @Test
  public void testFetchAll() {

    Collection<DbStudentEntity> actualStudentDbList = dbStudentsObj.fetchAll();
    System.out.println(" ------------ Start : Getting Values -------------------");
    actualStudentDbList.forEach(
        (stuObj) -> {
          ActiveDbStudentEntity active = (ActiveDbStudentEntity) stuObj;
          System.out.println("Name : " + active.name());
          System.out.println("Address : " + active.address());
          System.out.println("RollNo : " + active.lazyInstance().rollNo());
          System.out.println("College Code : " + active.lazyInstance().collegeCode());
          System.out.println("Course Code : " + active.lazyInstance().courseCode());
        });
    System.out.println("--------------- End : Getting Values ------------------");
    assertTrue(actualStudentDbList.size() == existingDbStudents.size());
    assertTrue(actualStudentDbList.containsAll(existingDbStudents));
  }

  
  @DisplayName("Delete Student By Id")
  @Test
  public void testDeleteBy() {
    // delete object
    dbStudentsObj.deleteBy(id);
    DbStudentEntity actualStudentObj = dbStudentsObj.getBy(id).orElse(null);
    assertTrue(actualStudentObj == null);
  }

  
  @DisplayName("Delete All Students")
  @Test
  public void deleteAll() {
    int expectedResultNoOfRows = dbStudentsObj.fetchAll().size();
    int count = dbStudentsObj.deleteAll();
    assertTrue(expectedResultNoOfRows == count);
  }
}
