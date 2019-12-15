package com.kodecamp.db;

import static org.junit.Assert.assertTrue;

// import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kodecamp.db.commons.DbConnection;
import com.kodecamp.db.course.DbCourseEntity;
import com.kodecamp.db.course.DbCourseEntityManager;
import com.kodecamp.db.course.DbCourseEntityManagerImpl;
import com.kodecamp.db.course.LazyDbCourseEntityImpl;

@Disabled
public class DisabledTestCourseEntity {
  private static Connection conn;
  private static DbCourseEntity dbCourseEntityObj;
  private static DbCourseEntityManager bdCourseEntityManagerObj;
  private static String courseCode = "BC12";

  @BeforeAll
  public static void setUp() {
    conn = DbConnection.open();
    dbCourseEntityObj = new LazyDbCourseEntityImpl(conn, courseCode);
    bdCourseEntityManagerObj = new DbCourseEntityManagerImpl(conn);
  }

  @BeforeEach
  public void testDataCreation() {

    conn = DbConnection.open();
    bdCourseEntityManagerObj = new DbCourseEntityManagerImpl(conn);

    bdCourseEntityManagerObj.create("BCA", "BC12", 20, 20).orElse(null);
  }

  @AfterEach
  public void delete() {
    bdCourseEntityManagerObj.deleteBy(courseCode);
  }

  @AfterAll
  public static void deleteAll() {
    bdCourseEntityManagerObj.deleteAll();
  }
  
  @DisplayName("Rename Course")
  @Test
  public void testRename() {
    final String chnageValue = "BCAA";
    DbCourseEntity actualDbCouseObj = bdCourseEntityManagerObj.getBy(courseCode).orElse(null);
    actualDbCouseObj.rename(chnageValue);
    assertTrue(actualDbCouseObj.name().equals(chnageValue));
  }

  @DisplayName("Update Seats")
  @Test
  public void testUpdateSeats() {
    final int newValue = 30;
    DbCourseEntity actualDbCouseObj = bdCourseEntityManagerObj.getBy(courseCode).orElse(null);
    actualDbCouseObj.seats();
    dbCourseEntityObj.updateSeats(newValue);
    assertTrue(actualDbCouseObj.seats() == (newValue));
  }

  @DisplayName("Get Course Name")
  @Test
  public void testGetName() {
    String actualValue = "BCA";
    String expectedValue = dbCourseEntityObj.name();
    assertTrue(actualValue.equals(expectedValue));
  }

  @DisplayName("Get Available Seats")
  @Test
  public void testGetAvailableSeats() {
    int actualValue = 20;
    int expectedValue = dbCourseEntityObj.availbaleSeats();
    assertTrue(actualValue == expectedValue);
  }

  @DisplayName("Get Seats")
  @Test
  public void testGetSeats() {
    int actualValue = 20;
    int expectedValue = dbCourseEntityObj.availbaleSeats();
    assertTrue(actualValue == expectedValue);
  }
}
