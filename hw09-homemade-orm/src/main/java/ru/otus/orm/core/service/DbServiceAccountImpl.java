package ru.otus.orm.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.orm.core.dao.AccountDao;
import ru.otus.orm.core.model.Account;
import ru.otus.orm.core.session_manager.SessionManager;

import java.util.Optional;

public class DbServiceAccountImpl implements DbServiceAccount {
    private static Logger logger = LogManager.getLogger(DbServiceAccountImpl.class);
    private final AccountDao accountDao;

    public DbServiceAccountImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public long saveAccount(Account account) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long accountRecordId = accountDao.saveAccount(account);
                sessionManager.commitSession();
                logger.info("Save Account to DB with record ID: " + accountRecordId);
                return accountRecordId;
            } catch (Exception exception) {
                logger.error(exception.getMessage(), exception);
                sessionManager.rollbackSession();
                throw new DbServiceException(exception);
            }
        }
    }

    @Override
    public void updateAccount(Account account) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                accountDao.updateAccount(account);
                sessionManager.commitSession();
                logger.info("Update Account to: " + account.toString());
            } catch (Exception exception) {
                logger.error(exception.getMessage(), exception);
                sessionManager.rollbackSession();
                throw new DbServiceException(exception);
            }
        }
    }

    @Override
    public Optional<Account> getAccount(long id) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> accountOptional = accountDao.findById(id);
                logger.info("Get Account from DB by ID: " + accountOptional.orElse(null));
                return accountOptional;
            } catch (Exception exception) {
                logger.error(exception.getMessage(), exception);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
