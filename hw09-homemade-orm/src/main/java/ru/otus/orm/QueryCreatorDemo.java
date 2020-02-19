package ru.otus.orm;

import ru.otus.orm.core.model.Account;
import ru.otus.orm.core.model.User;
import ru.otus.orm.jdbc.helper.DbQueryCreator;

public class QueryCreatorDemo {

    public static void main(String[] args) {
        var queryCreator = new DbQueryCreator();
        var user1 = new User(0, "User 1");
        var user2 = new User(1, "User 2");
        var account1 = new Account(2, "XXX", 10);
        var account2 = new Account(3, "ZZZ", 10);

        String insertQuery = queryCreator.createInsertQuery(account1);
        System.out.println("Insert: " + insertQuery);
        System.out.println();

        String updateQuery = queryCreator.createUpdateQuery(account2);
        System.out.println("Update: " + updateQuery);
        System.out.println();

        String selectQuery = queryCreator.createSelectQuery(account2);
        System.out.println("Select: " + selectQuery);
        System.out.println();
    }
}
