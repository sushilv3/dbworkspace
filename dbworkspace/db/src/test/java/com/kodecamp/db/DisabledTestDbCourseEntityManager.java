package com.kodecamp.db;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kodecamp.db.commons.DbConnection;
import com.kodecamp.db.course.DbCourseEntity;
import com.kodecamp.db.course.LazyDbCourseEntityImpl;
import com.kodecamp.db.course.DbCourseEntityManager;
import com.kodecamp.db.course.DbCourseEntityManagerImpl;

@Disabled
public class DisabledTestDbCourseEntityManager {
  private static Connection conn;

  private final String courseCode = "BC12";
  private static List<DbCourseEntity> courseList = new ArrayList<>();
  private static DbCourseEntityManager dbCourseEntityManagerObj;

  @BeforeAll
  static void setup() {
    conn = DbConnection.open();
    dbCourseEntityManagerObj = new DbCourseEntityManagerImpl(conn);
  }

  @BeforeEach
  public void createData() {
    courseList =
        List.of(
            dbCourseEntityManagerObj
                .create("BBA", "BB11", 20, 20)
                .orElse(new LazyDbCourseEntityImpl(conn, "")),
            dbCourseEntityManagerObj
                .create("BCA", "BC12", 30, 30)
                .orElse(new LazyDbCourseEntityImpl(conn, "")),
            dbCourseEntityManagerObj
                .create("B.TECH", "BT13", 40, 40)
                .orElse(new LazyDbCourseEntityImpl(conn, "")),
            dbCourseEntityManagerObj
                .create("M.TECH", "MT14", 25, 25)
                .orElse(new LazyDbCourseEntityImpl(conn, "")),
            dbCourseEntityManagerObj
                .create("MBA", "MB15", 10, 10)
                .orElse(new LazyDbCourseEntityImpl(conn, "")));

    //    DbCourseEntity course =
    //        dbCourseEntityManagerObj
    //            .create("BBA", "BB11", 20, 20)
    //            .orElse(new DbCourseEntityImpl(conn, ""));
    //    courseList.add(course);
  }

  @AfterEach
  public void deleteAll() {
    dbCourseEntityManagerObj.deleteAll();
  }

  @DisplayName("Create Courses")
  @Test
  public void testCreateCourse() {
    DbCourseEntity expectedDbCourseEntityObj = new LazyDbCourseEntityImpl(conn, courseCode);
   
    int index = courseList.indexOf(expectedDbCourseEntityObj);
    System.out.println("---------------------------index value in test" + index);
    assertTrue(index >= 0);
  }

  @DisplayName("Fetch All Records")
  @Test
  public void testFetchAll() {
    assertTrue(courseList.size() == dbCourseEntityManagerObj.fetchAll().size());
    assertTrue(courseList.containsAll(dbCourseEntityManagerObj.fetchAll()));
  }

  @DisplayName("Get Record By")
  @Test
  public void testGetBy() {
    DbCourseEntity actualObj =
        dbCourseEntityManagerObj.getBy(courseCode).orElse(new LazyDbCourseEntityImpl(conn, ""));
    DbCourseEntity expectedObj = new LazyDbCourseEntityImpl(conn, courseCode);
    assertTrue(expectedObj.equals(actualObj));
  }

  @DisplayName("Delete Record By")
  @Test
  public void testDeleteBy() {
    DbCourseEntity dbCourseObj = new LazyDbCourseEntityImpl(conn, "");
    dbCourseEntityManagerObj.deleteBy(courseCode);
    DbCourseEntity actualObj =
        dbCourseEntityManagerObj.getBy(courseCode).orElse(new LazyDbCourseEntityImpl(conn, ""));
    assertTrue(dbCourseObj.equals(actualObj));
  }

  @DisplayName("Delete All Records")
  @Test
  public void testDeleteAll() {
    int expectedRows = dbCourseEntityManagerObj.fetchAll().size();
    int count = dbCourseEntityManagerObj.deleteAll();
    assertTrue(expectedRows == count);
  }
}
