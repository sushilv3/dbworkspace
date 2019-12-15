package com.kodecamp.db;

import java.sql.Connection;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kodecamp.db.college.DbCollegeEntity;
import com.kodecamp.db.college.DbCollegeEntityManagerImpl;
import com.kodecamp.db.college.DbCollegeEntityManger;
import com.kodecamp.db.commons.DbConnection;

@Disabled
public class DisabledTestCollegeEntityManager {
  private static Connection conn;
  private static DbCollegeEntityManger dbCollegeEntityManagerObj;
  //  private static DbCollegeEntity dbCollegeEntityObj;

  @BeforeAll
  public static void setUp() {
    System.out.println("############# setUp");
    conn = DbConnection.open();
    dbCollegeEntityManagerObj = new DbCollegeEntityManagerImpl(conn);
  }

  @BeforeEach
  public void createData() {
    System.out.println("CREATE DATA : " + dbCollegeEntityManagerObj);
    Optional<DbCollegeEntity> collegeEntity =
        dbCollegeEntityManagerObj.create(
            "1001", "United College of Engineering and Management", "Allahabad");
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~ College Entity : " + collegeEntity);
    DbCollegeEntity entity = collegeEntity.orElse(null);
    //    dbCollegeList =
    //        List.of(
    //            dbCollegeEntityManagerObj
    //                .create("Uni001", "United College of Engineering and Management", "Allahabad")
    //                .orElse(new DbCollegeEntityImpl("", conn)),
    //            dbCollegeEntityManagerObj
    //                .create("Bab001", "Babu Banarasi Das University", "Noida")
    //                .orElse(new DbCollegeEntityImpl("", conn)),
    //            dbCollegeEntityManagerObj
    //                .create("Sar001", "Saroj institute Of Technology & Management", "Lucknow")
    //                .orElse(new DbCollegeEntityImpl("", conn)),
    //            dbCollegeEntityManagerObj
    //                .create(
    //                    "Ram001",
    //                    "Shri Ramswaroop Memorial College of Engineering and Management",
    //                    "Barabanki")
    //                .orElse(new DbCollegeEntityImpl("", conn)),
    //            dbCollegeEntityManagerObj
    //                .create("Aza001", "Azad Institute of Engineering and Technology", "Kanpur")
    //                .orElse(new DbCollegeEntityImpl("", conn)));
  }

  @AfterEach
  public void cleanUp() {
    dbCollegeEntityManagerObj.deleteAll();
  }


  @DisplayName("Fetch All")
  @Test
  public void testfetchAll() {
    int NumberOfRows = dbCollegeEntityManagerObj.fetchAll().size();
    System.out.println("+++++++++++++++++++++++" + NumberOfRows);
    //    assertTrue(dbCollegeList.size() == dbCollegeEntityManagerObj.fetchAll().size());
  }
}
