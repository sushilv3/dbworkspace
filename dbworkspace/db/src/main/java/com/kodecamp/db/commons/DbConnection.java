package com.kodecamp.db.commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
  // H2 config
  private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/test-college-db";
  private static final String USER = "sushil";
  private static final String PASSWORD = "sushil";
  private static Connection conn = null;

  // mysql config
  //    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/collegedb";
  //    private static final String USER = "root";
  //    private static final String PASSWORD = "123456";

  public static synchronized Connection open() {
    try {
      conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    } catch (SQLException e) {
      System.err.println("Something went wrong while connection to database");
    }
    return conn;
  }

  public static synchronized Connection open(final boolean isExisting) {
    try {
      if (isExisting) {
        conn = conn != null ? conn : DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        return conn;
      }
      conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    } catch (SQLException e) {
      System.err.println("Something went wrong while connection to database");
    }
    return conn;
  }

  public static synchronized boolean close(final Connection conn) {
    boolean isClosed = false;
    try {
      if (!conn.isClosed()) {
        conn.close();
        isClosed = true;
      }
    } catch (SQLException e) {
      System.out.println("Error occured while closing connection.");
      
    }
    return isClosed;
  }
}
