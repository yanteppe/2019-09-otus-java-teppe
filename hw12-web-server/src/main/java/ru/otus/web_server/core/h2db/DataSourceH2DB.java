package ru.otus.web_server.core.h2db;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DataSourceH2DB implements DataSource {
   private static final String URL = "jdbc:h2:/Users/admin/Develop/Java/OtusJava/hw12-web-server/src/main/resources/db/H2DB.db;MV_STORE=false";
//   private static final String URL = "jdbc:h2:mem:H2DB;DB_CLOSE_DELAY=-1";

   @Override
   public Connection getConnection() throws SQLException {
      Connection connection = DriverManager.getConnection(URL);
      connection.setAutoCommit(false);
      return connection;
   }

   @Override
   public Connection getConnection(String username, String password) {
      return null;
   }

   @Override
   public PrintWriter getLogWriter() {
      return null;
   }

   @Override
   public void setLogWriter(PrintWriter out) {
      throw new UnsupportedOperationException();
   }

   @Override
   public int getLoginTimeout() {
      throw new UnsupportedOperationException();
   }

   @Override
   public void setLoginTimeout(int seconds) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Logger getParentLogger() {
      throw new UnsupportedOperationException();
   }

   @Override
   public <T> T unwrap(Class<T> iface) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean isWrapperFor(Class<?> iface) {
      throw new UnsupportedOperationException();
   }
}
