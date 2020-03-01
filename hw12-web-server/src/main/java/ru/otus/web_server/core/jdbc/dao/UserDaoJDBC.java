package ru.otus.web_server.core.jdbc.dao;

import ru.otus.web_server.core.dao.UserDao;
import ru.otus.web_server.core.model.User;
import ru.otus.web_server.core.session_manager.SessionManager;
import ru.otus.web_server.core.jdbc.DbExecutor;
import ru.otus.web_server.core.jdbc.session_manager.SessionManagerJdbc;

import java.util.List;
import java.util.Optional;

public class UserDaoJDBC implements UserDao {
    private final JDBCTemplate jdbcTemplate;
    private final SessionManagerJdbc sessionManager;

    public UserDaoJDBC(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor) {
        this.sessionManager = sessionManager;
        this.jdbcTemplate = new JDBCTemplate(sessionManager, dbExecutor);
    }

    @Override
    public long saveUser(User user) {
        jdbcTemplate.create(user);
        return jdbcTemplate.getRecordId();
    }

    @Override
    public void updateUser(User user) {
        jdbcTemplate.update(user);
    }

    @Override
    public Optional<User> findById(long id) {
        return jdbcTemplate.load(id, User.class);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.getAllRecords(User.class);
    }

    @Override
    public Optional<User> findUserByParameter(String paremeter) {
        return jdbcTemplate.load(User.class, paremeter);
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
