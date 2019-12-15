package com.kodecamp.db.commons;

import com.kodecamp.db.commons.DbAware.ResultObj;

public interface IDbAware<T> {

  /**
   * This method accepts the actual query to be executed. This method uses the <code>
   * java.sql.Statement</code> to execute the query. It is recommended to supply the sanitized
   * queryString to this method.
   *
   * @see <code>executeUpdate(final String queryString, final Object[] values)
   *     </code>
   * @param query
   * @return
   */
  public int executeUpdate(final String query);

  /**
   * @param queryString
   * @param values
   * @return
   */
  public int executeUpdate(final String queryString, final Object[] values);

  /**
   * @param query
   * @return
   */
  public ResultObj<T> executeQuery(final String query);

  public ResultObj<T> executeQuery(final String parameterizedQuery, final Object[] values);
}
