package com.kodecamp.db.commons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public final class JdbcSession {
  private Connection conn;

  public JdbcSession() {
    this.conn = DbConnection.open();
  }

  

  public void closeSession() {}
  //
  public PreparedStatement prepareStatement(final String parameterizedQuery) {
    PreparedStatement pstmt = null;
    try {
      pstmt = this.conn.prepareStatement(parameterizedQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return pstmt;
  }

  public Statement createStatement() {
    Statement pstmt = null;
    try {
      pstmt = this.conn.createStatement();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return pstmt;
  }
}
