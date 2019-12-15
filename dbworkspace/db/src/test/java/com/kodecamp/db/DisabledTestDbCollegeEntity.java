package com.kodecamp.db;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.kodecamp.db.college.DbCollegeEntity;
import com.kodecamp.db.college.DbCollegeEntityManagerImpl;
import com.kodecamp.db.college.DbCollegeEntityManger;
import com.kodecamp.db.college.LazyDbCollegeEntityImpl;
import com.kodecamp.db.commons.DbConnection;
import com.kodecamp.db.student.DbStudentEnityManger;

@Disabled
public class DisabledTestDbCollegeEntity {
  private static Connection conn;
  private static DbCollegeEntity dbCollegeEntityObj;
  private static DbCollegeEntityManger bdCollegeEntityManagerObj;
  private static DbStudentEnityManger dbStudentEm;
  private static String regNo = "Uni001";

  @BeforeAll
  public static void setUp() {
    conn = DbConnection.open();
    dbCollegeEntityObj = new LazyDbCollegeEntityImpl(regNo, dbStudentEm, conn);
    bdCollegeEntityManagerObj = new DbCollegeEntityManagerImpl(conn);
  }

  @BeforeEach
  public void testDataCreation() {
    dbCollegeEntityObj =
        bdCollegeEntityManagerObj
            .create("Uni001", "United College of Engineering and Management	", "Allahabad")
            .orElse(null);
  }

  @AfterEach
  public void delete() {
    bdCollegeEntityManagerObj.deleteBy(regNo);
  }

  @Disabled
  @AfterAll
  public static void deleteAll() {
    bdCollegeEntityManagerObj.deleteAll();
  }

  @Test
  public void rename() {
    String newName = "UCEM";
    dbCollegeEntityObj.rename(newName);
    DbCollegeEntity dbCollegeEntityActualObj = bdCollegeEntityManagerObj.getBy(regNo).orElse(null);
    assertTrue(dbCollegeEntityObj.equals(dbCollegeEntityActualObj));
  }
}
