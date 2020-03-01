package ru.otus.web_server.core.jdbc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Class executor of statement to the database
 *
 * @param <T> object for database
 */
public class DbExecutor<T> {
    private static Logger logger = LogManager.getLogger(DbExecutor.class);

    public long insertRecord(Connection connection, String sqlStatement, List<String> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setString(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            connection.rollback(savePoint);
            logger.error(sqlException.getMessage(), sqlException);
            throw sqlException;
        }
    }

    public Optional<T> selectRecord(Connection connection, String sql, long id, Function<ResultSet, T> resultSetHandler) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return Optional.ofNullable(resultSetHandler.apply(resultSet));
            }
        }
    }

    public Optional<T> selectRecord(Connection connection, String sql, Function<ResultSet, T> resultSetHandler) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(, parameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return Optional.ofNullable(resultSetHandler.apply(resultSet));
            }
        }
    }

    public ResultSet selectAllRecords(Connection connection, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet;
//                return Optional.ofNullable(resultSetHandler.apply(resultSet));
            }
        }
    }

//    public Optional<T> selectAllRecords(Connection connection, String sql, Function<ResultSet, T> resultSetHandler) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
////            preparedStatement.setLong(1, id);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                return Optional.ofNullable(resultSetHandler.apply(resultSet));
//            }
//        }
//    }

    public void updateRecord(Connection connection, String sqlStatement, List<String> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setString(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            connection.rollback(savePoint);
            logger.error(sqlException.getMessage(), sqlException);
            throw sqlException;
        }
    }
}
