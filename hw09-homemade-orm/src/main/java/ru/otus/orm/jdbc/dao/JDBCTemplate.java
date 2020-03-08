package ru.otus.orm.jdbc.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.orm.jdbc.DbExecutor;
import ru.otus.orm.jdbc.helper.DbStatementCreator;
import ru.otus.orm.jdbc.session_manager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
        String selectStatement = statementCreator.createSelectStatement(clazz);
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

    public long getRecordId() {
        return recordId;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
