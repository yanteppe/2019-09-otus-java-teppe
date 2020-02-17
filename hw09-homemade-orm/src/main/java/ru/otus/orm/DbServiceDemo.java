package ru.otus.orm;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.otus.orm.core.dao.UserDao;
import ru.otus.orm.core.model.User;
import ru.otus.orm.core.service.DBServiceUser;
import ru.otus.orm.core.service.DbServiceUserImpl;
import ru.otus.orm.h2db.DataSourceH2DB;
import ru.otus.orm.jdbc.DbExecutor;
import ru.otus.orm.jdbc.dao.UserDaoJdbc;
import ru.otus.orm.jdbc.session_manager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DbServiceDemo {
    private static Logger logger = LogManager.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        DataSource dataSource = new DataSourceH2DB();
        DbServiceDemo demo = new DbServiceDemo();
        demo.createTable(dataSource);
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<User> dbExecutor = new DbExecutor<>();
        UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
        long id = dbServiceUser.saveUser(new User(0, "dbServiceUser"));
        Optional<User> user = dbServiceUser.getUser(id);

        System.out.println("User in DB: " + user);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name: " + crUser.getName()),
                () -> logger.info("user was not created")
        );
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user (id bigint(20) not null auto_increment, name varchar(50))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
