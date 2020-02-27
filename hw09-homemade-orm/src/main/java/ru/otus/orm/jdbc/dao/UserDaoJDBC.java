package ru.otus.orm.jdbc.dao;

import ru.otus.orm.core.dao.UserDao;
import ru.otus.orm.core.model.User;
import ru.otus.orm.core.session_manager.SessionManager;
import ru.otus.orm.jdbc.DbExecutor;
import ru.otus.orm.jdbc.session_manager.SessionManagerJdbc;

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
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
