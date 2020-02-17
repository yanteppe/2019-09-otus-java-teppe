package ru.otus.orm.jdbc.helper;

import ru.otus.orm.jdbc.helper.ObjectDataCollector;

/**
 * Database Query Creator
 *
 * @param <T> Some object to save to the database
 */
public class DbQueryCreator<T> {
    private ObjectDataCollector objDataCollector;
    private StringBuilder insertQuery;
    private StringBuilder updateQuery;
    private StringBuilder selectQuery;

    public DbQueryCreator() {
        this.objDataCollector = new ObjectDataCollector();
    }

    public String createInsertQuery(T objectData) {
        String tableName = objectData.getClass().getName();
//        insertQuery = new StringBuilder();
//        insertQuery
//                .append("INSERT ")
//                .append("INTO ")
//                .append(tableName).append(" ")
//                .append("VALUES")
        return "";
    }

    public String createUpdateQuery(T objectData) {


        return "";
    }

    public String createSelectQuery(T objectData) {

        return "";
    }

//    public <T> T load(long id, Class<T> clazz) {
//
//        return null;
//    }
}