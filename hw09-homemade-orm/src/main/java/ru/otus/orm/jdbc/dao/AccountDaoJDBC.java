package ru.otus.orm.jdbc.dao;

import ru.otus.orm.core.dao.AccountDao;
import ru.otus.orm.core.model.Account;
import ru.otus.orm.core.session_manager.SessionManager;
import ru.otus.orm.jdbc.DbExecutor;
import ru.otus.orm.jdbc.session_manager.SessionManagerJdbc;

import java.util.Optional;

public class AccountDaoJDBC implements AccountDao {
    private final JDBCTemplate jdbcTemplate;
    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<Account> dbExecutor;

    public AccountDaoJDBC(SessionManagerJdbc sessionManager, DbExecutor<Account> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.jdbcTemplate = new JDBCTemplate(sessionManager, dbExecutor);
    }

    @Override
    public long saveAccount(Account account) {
        jdbcTemplate.create(account);
        return jdbcTemplate.getRecordId();
    }

    @Override
    public void updateAccount(Account account) {
        jdbcTemplate.update(account);
    }

    @Override
    public Optional<Account> findById(long id) {
        return jdbcTemplate.load(id, Account.class);
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
