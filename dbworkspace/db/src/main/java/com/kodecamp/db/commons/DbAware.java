package com.kodecamp.db.commons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class DbAware<T> implements IDbAware<T> {
  private final Connection conn;

  public DbAware(final Connection conn) {
    if (conn == null) {
      throw new RuntimeException("Please supply valid connection object. Connection is NULL");
    }
    this.conn = conn;
  }

  @Override
  /**
   * @param query
   * @return
   */
  public final int executeUpdate(final String parameterizedQuery, final Object[] values) {
    //    System.out.println("##### Query : " + parameterizedQuery);
    int count = 0;

    try {
      final PreparedStatement pstmt = conn.prepareStatement(parameterizedQuery);
      

      // simulating traditional for(int index = 0; index < values.length; index++)
      IntStream.range(0, values.length)
          .forEach((index) -> this.setParameterAt(index, values[index], pstmt));

      System.out.println("@@@@ Prepared Query : " + pstmt);
      count = pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Something went wrong while creating new student");
      e.printStackTrace();
    }

    return count;
  }

  private void setParameterAt(final int index, final Object value, final PreparedStatement pstmt) {
    try {
      pstmt.setObject(index + 1, value);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      //      e.printStackTrace();
    }
  }

  @Override
  public final int executeUpdate(final String queryString) {
    //    System.out.println("##### Query : " + queryString);
    int count = 0;

    try {
      final Statement pstmt = conn.createStatement();
      System.out.println("@@@@ Prepared Query : " + pstmt);
      count = pstmt.executeUpdate(queryString);
    } catch (SQLException e) {
      System.out.println("Something went wrong while creating new student");
      e.printStackTrace();
    }

    return count;
  }

  /**
   * @param query
   * @return
   */
  @Override
  public final ResultObj<T> executeQuery(final String query) {
    //    System.out.println("##### Query : " + query);
    ResultSet rs = null;
    try {
      rs = conn.createStatement().executeQuery(query);

    } catch (SQLException e) {
      System.out.println(
          "######## ERROR : Something went wrong while fetching Students : " + query);
      e.printStackTrace();
    }
    return this.mapResultSet(rs);
  }

  @Override
  public final ResultObj<T> executeQuery(final String parameterizedQuery, final Object[] values) {
    //    System.out.println("##### Query : " + parameterizedQuery);
    ResultSet rs = null;
    try {
      final PreparedStatement pstmt = conn.prepareStatement(parameterizedQuery);
      IntStream.range(0, values.length)
          .forEach((index) -> this.setParameterAt(index, values[index], pstmt));

      System.out.println("@@@@ Prepared Query : " + pstmt);
      rs = pstmt.executeQuery();
      

    } catch (SQLException e) {
      System.out.println(
          "######## ERROR : Something went wrong while fetching Students : " + parameterizedQuery);
      e.printStackTrace();
    }
    return this.mapResultSet(rs);
  }

  private final ResultObj<T> mapResultSet(final ResultSet rs) {
    List<T> records = new ArrayList<>();
    try {
      List<String> columns = this.getColums(rs);
      while (rs.next()) {
        Map<String, Object> row =
            columns
                .stream()
                .collect(
                    Collectors.toMap(
                        columnName -> columnName, (columnName) -> this.mapper(rs, columnName)));
        T obj = this.mapToObject(row);
        records.add(obj);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ResultObj<T>(records);
  }

  private List<String> getColums(final ResultSet rs) throws SQLException {
    final List<String> columns = new ArrayList<>();
    ResultSetMetaData rsMetaData = rs.getMetaData();
    int columnCount = rsMetaData.getColumnCount();
    for (int i = 1; i <= columnCount; i++) {
      columns.add(rsMetaData.getColumnName(i));
    }
    return columns;
  }

  /**
   * @param rs
   * @param columnName
   * @return
   */
  private Object mapper(final ResultSet rs, final String columnName) {
    try {
      return rs.getObject(columnName);
    } catch (SQLException e) {

      e.printStackTrace();
    }
    return null;
  }

  /**
   * This method maps the row into specific DbEntity
   *
   * @param row
   * @return
   */
  protected abstract T mapToObject(final Map<String, Object> row);

  public static final class ResultObj<T> {
    private final List<T> rows;

    ResultObj(List<T> rows) {
      this.rows = rows;
    }

    public List<T> getMany() {
      return this.rows;
    }

    public Optional<T> getOne() {
      return Optional.ofNullable(this.rows.isEmpty() ? null : rows.get(0));
    }
  }

  
}
