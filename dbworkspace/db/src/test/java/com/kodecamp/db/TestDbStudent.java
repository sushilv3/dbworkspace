package com.kodecamp.db;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kodecamp.db.commons.DbConnection;
import com.kodecamp.db.student.ActiveDbStudentEntity;
import com.kodecamp.db.student.DbStudentEnityManger;
import com.kodecamp.db.student.DbStudentEntity;
import com.kodecamp.db.student.DbStudentEntityManagerImpl;
import com.kodecamp.db.student.StudentId;


public class TestDbStudent {
  private static Connection conn;
  private static DbStudentEntity createdDbStudentObj;
  private static DbStudentEnityManger studentsEntityManager;
  private static String id = "At001";

  @BeforeAll
  public static void testDataCreation() {
    conn = DbConnection.open();
    studentsEntityManager = new DbStudentEntityManagerImpl(conn);
    final DbStudentEntity emptyDbStudent = null;

    // TODO : add more values
    createdDbStudentObj =
        studentsEntityManager
            .create(new StudentId("At001"), "Atul", "Ruchikhand", "At012", "Bab001", "BC12")
            .orElse(emptyDbStudent);
  }

  //  @BeforeEach
  //  @AfterEach
  //  public void refreshData() {
  //    ((ActiveDbStudentEntity) createdDbStudentObj).refresh();
  //  }

  @AfterAll
  public static void connectionClose() {
    studentsEntityManager.deleteBy(id);
    DbConnection.close(conn);
  }

  @Disabled
  @DisplayName("Rename Student")
  @Test
  public void testDbStudentRename() {
    final String newValue = "Bablu";
    DbStudentEntity dbStudentObj = studentsEntityManager.getBy(id).orElse(null);
    dbStudentObj.rename(newValue);
    
    DbStudentEntity updatedStudent = studentsEntityManager.getBy(id).orElse(null);
    System.out.println("Name : " + dbStudentObj.name());
    System.out.println("Updated Name : " + updatedStudent.name());

    assertTrue(dbStudentObj.name().equals(updatedStudent.name()));
  }


  @DisplayName("Change Address Student")
  @Test
  public void testDbStudentChangeAddress() {
    final String newAddress = "MirzaPur";
    DbStudentEntity dbStudentObj = studentsEntityManager.getBy(id).orElse(null);
    System.out.println("------------ Before Update -----------");
    System.out.println("111 : Object  : " + createdDbStudentObj);
    System.out.println("222 : Object  : " + dbStudentObj);
    dbStudentObj.changeAddress(newAddress);

    System.out.println("------------ After Update in 222  -----------");
    ((ActiveDbStudentEntity) createdDbStudentObj).refresh();
    System.out.println("111 : Object : " + createdDbStudentObj);
    System.out.println("222 : Object : " + dbStudentObj);

    assertTrue(createdDbStudentObj.changeAddress("New Address"));

    //    System.out.println("Address : " + createdDbStudentObj.address());
    //    System.out.println("New Address : " + dbStudentObj.address());
    //    assertTrue(dbStudentObj.equals(createdDbStudentObj));
  }

  @Disabled
  @DisplayName("Student Name")
  @Test
  public void testDbStudentGetName() {
    DbStudentEntity dbStudentObj = studentsEntityManager.getBy(id).orElse(null);
    assertTrue(dbStudentObj.equals(createdDbStudentObj));
  }
}
