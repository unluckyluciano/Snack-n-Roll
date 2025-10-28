package snacknroll.util;

import javax.sql.DataSource;
import javax.naming.InitialContext;

public final class DataSourceProvider {
  private static DataSource DS;
  private DataSourceProvider(){}

  public static DataSource get() {
    if (DS == null) {
      try {
        DS = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/snacknroll");
      } catch (Exception e) {
        throw new RuntimeException("jdbc/snacknroll non trovato", e);
      }
    }
    return DS;
  }
}
