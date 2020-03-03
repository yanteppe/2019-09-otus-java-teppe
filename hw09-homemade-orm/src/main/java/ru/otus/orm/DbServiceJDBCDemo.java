package ru.otus.orm;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.otus.orm.core.dao.AccountDao;
import ru.otus.orm.core.dao.UserDao;
import ru.otus.orm.core.model.Account;
import ru.otus.orm.core.model.User;
import ru.otus.orm.core.service.DBServiceUser;
import ru.otus.orm.core.service.DbServiceAccount;
import ru.otus.orm.core.service.DbServiceAccountImpl;
import ru.otus.orm.core.service.DbServiceUserImpl;
import ru.otus.orm.h2db.DataSourceH2DB;
import ru.otus.orm.jdbc.DbExecutor;
import ru.otus.orm.jdbc.dao.AccountDaoJDBC;
import ru.otus.orm.jdbc.dao.UserDaoJDBC;
import ru.otus.orm.jdbc.session_manager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbServiceJDBCDemo {
    private static Logger logger = LogManager.getLogger(DbServiceJDBCDemo.class);

    public static void main(String[] args) throws Exception {
        System.out.println("DbServiceDemo\n");
        // Preconditions
        DataSourceH2DB dataSource = new DataSourceH2DB();
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<User> userDbExecutor = new DbExecutor<>();
        DbExecutor<Account> accountDbExecutor = new DbExecutor<>();
        UserDao userDao = new UserDaoJDBC(sessionManager, userDbExecutor);
        AccountDao accountDao = new AccountDaoJDBC(sessionManager, accountDbExecutor);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
        DbServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);
        DbServiceJDBCDemo demo = new DbServiceJDBCDemo();
        demo.createTables(dataSource);

        // User Demo
        System.out.println("\nUSER DEMO:");
        var user = new User(1, "X");
        logger.info("CREATE USER: " + user.toString());
        long userId = dbServiceUser.saveUser(user);
        dbServiceUser.getUser(userId);
        user.setName("Y");
        dbServiceUser.updateUser(user);
        dbServiceUser.getUser(userId);

        System.out.println();

        // Account Demo
        System.out.println("ACCOUNT DEMO:");
        var account = new Account(1, "Account X", BigDecimal.ONE);
        logger.info("CREATE ACCOUNT: " + account.toString());
        long accId = dbServiceAccount.saveAccount(account);
        dbServiceAccount.getAccount(accId);
        account.setType("Account Y");
        account.setRest(BigDecimal.TEN);
        dbServiceAccount.updateAccount(account);
        dbServiceAccount.getAccount(accId);
    }

    private void createTables(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection
                     .prepareStatement("create table user(id long auto_increment, name varchar(50))")) {
            pst.executeUpdate();
            logger.info("Table User created");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection
                     .prepareStatement(
                             "create table account(number bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
            logger.info("Table Account created");
        }
    }
}