package ru.otus.web_server.core.jdbc.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.otus.web_server.core.jdbc.DbExecutor;
import ru.otus.web_server.core.jdbc.helper.DbStatementCreator;
import ru.otus.web_server.core.jdbc.session_manager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCTemplate<T> {
   private static Logger logger = LogManager.getLogger(JDBCTemplate.class);
   private final SessionManagerJdbc sessionManager;
   private final DbExecutor<T> dbExecutor;
   private DbStatementCreator<T> statementCreator;
   private long recordId;

   public JDBCTemplate(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
      this.sessionManager = sessionManager;
      this.dbExecutor = dbExecutor;
      this.statementCreator = new DbStatementCreator<>();
   }

   public void create(T object) {
      String insertStatement = statementCreator.createInsertStatement(object);
      List<String> listValues = statementCreator.getListValues();
      try {
         recordId = dbExecutor.insertRecord(getConnection(), insertStatement, listValues);
      } catch (Exception exception) {
         logger.error(exception.getMessage(), exception);
         throw new JDBCTemplateException(exception);
      }
   }

   public void update(T object) {
      String updateStatement = statementCreator.createUpdateStatement(object);
      List<String> values = statementCreator.getListValues();
      try {
         dbExecutor.updateRecord(getConnection(), updateStatement, values);
      } catch (SQLException sqlException) {
         sqlException.printStackTrace();
      }
   }

   public Optional<T> load(long id, Class<T> clazz) {
      String selectStatement = statementCreator.createSelectByIdStatement(clazz);
      try {
         T object = clazz.getDeclaredConstructor().newInstance();
         return dbExecutor.selectRecord(getConnection(), selectStatement, id, resultSet -> {
            try {
               if (resultSet.next()) {
                  // Create object instance
                  for (Field field : clazz.getDeclaredFields()) {
                     field.setAccessible(true);
                     field.set(object, resultSet.getObject(field.getName()));
                  }
               }
            } catch (SQLException | IllegalAccessException sqlException) {
               logger.error(sqlException.getMessage(), sqlException);
            }
            return object;
         });
      } catch (Exception exception) {
         logger.error(exception.getMessage(), exception);
      }
      return Optional.empty();
   }

   public Optional<T> load(Class<T> clazz, String parameter) {
      String selectStatement = statementCreator.createSelectByParamStatement(clazz, parameter);
      try {
         T object = clazz.getDeclaredConstructor().newInstance();
         return dbExecutor.selectRecord(getConnection(), selectStatement, resultSet -> {
            try {
               if (resultSet.next()) {
                  // Create object instance
                  for (Field field : clazz.getDeclaredFields()) {
                     field.setAccessible(true);
                     field.set(object, resultSet.getObject(field.getName()));
                  }
               }
            } catch (SQLException | IllegalAccessException sqlException) {
               logger.error(sqlException.getMessage(), sqlException);
            }
            return object;
         });
      } catch (Exception exception) {
         logger.error(exception.getMessage(), exception);
      }
      return Optional.empty();
   }

   public List<T> getAllRecords(Class<T> clazz) {
      List<T> objectsFromDb = new ArrayList<>();
      String selectAllRecordsStatement = statementCreator.createSelectAllStatement(clazz);
      try {
         ResultSet resultSet = dbExecutor.selectAllRecords(getConnection(), selectAllRecordsStatement);
         while (resultSet.next()) {
            T object = clazz.getDeclaredConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
               field.setAccessible(true);
               field.set(object, resultSet.getObject(field.getName()));
            }
            objectsFromDb.add(object);
         }
      } catch (Exception exception) {
         logger.error(exception.getMessage(), exception);
      }
      return objectsFromDb;
   }

   public long getRecordId() {
      return recordId;
   }

   private Connection getConnection() {
      return sessionManager.getCurrentSession().getConnection();
   }
}
