package ru.otus.web_server.core.h2db;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2DBInit {
    private static final String CREATE_TABLE_STATEMENT =
          "create table user(id long auto_increment, name varchar(40), login varchar(40), password varchar(40))";
    private static Logger logger = LogManager.getLogger(H2DBInit.class);

    public void initTables(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection
                     .prepareStatement(CREATE_TABLE_STATEMENT)) {
            pst.executeUpdate();
            logger.info("Table User created");
        }
    }
}
