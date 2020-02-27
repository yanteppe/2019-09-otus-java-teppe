package ru.otus.orm.core.dao;

import ru.otus.orm.core.model.Account;
import ru.otus.orm.core.session_manager.SessionManager;

import java.util.Optional;

public interface AccountDao {

    long saveAccount(Account account);

    void updateAccount(Account account);

    Optional<Account> findById(long id);

    SessionManager getSessionManager();
}
