package ru.otus.orm.jdbc.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.otus.orm.core.dao.UserDao;
import ru.otus.orm.core.dao.UserDaoException;
import ru.otus.orm.core.model.User;
import ru.otus.orm.core.session_manager.SessionManager;
import ru.otus.orm.jdbc.DbExecutor;
import ru.otus.orm.jdbc.session_manager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

// TODO: добавить генератор запросов для добовляемого объекта в БД (Через рефлексию)
public class UserDaoJdbc implements UserDao {
    private static Logger logger = LogManager.getLogger(UserDaoJdbc.class);
    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<User> dbExecutor;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            return dbExecutor.selectRecord(getConnection(), "select id, name from user where id  = ?", id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return new User(resultSet.getLong("id"), resultSet.getString("name"));
                    }
                } catch (SQLException exception) {
                    logger.error(exception.getMessage(), exception);
                }
                return null;
            });
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {
        try {
            return dbExecutor.insertRecord(getConnection(), "insert into user(name) values (?)", Collections.singletonList(user.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
