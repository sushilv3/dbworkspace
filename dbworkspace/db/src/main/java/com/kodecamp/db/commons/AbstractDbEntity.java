package com.kodecamp.db.commons;

import java.sql.Connection;
import java.util.Map;

public class AbstractDbEntity extends DbAware<Object> {

  public AbstractDbEntity(Connection conn) {
    super(conn);
  }

  @Override
  protected Object mapToObject(Map<String, java.lang.Object> row) {
    Object[] value = new Object[1];
    row.forEach(
        (columnName, columnValue) -> {
          value[0] = columnValue;
        });
    return value[0];
  }
}
