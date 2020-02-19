package ru.otus.orm.jdbc.helper;


import ru.otus.orm.core.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * Database Query Creator
 *
 * @param <T> Some object to save to the database
 */
public class DbQueryCreator<T> {
    private ObjectDataCollector objDataCollector;

    public String createInsertQuery(T objectData) {
        objDataCollector = new ObjectDataCollector();
        var values = new StringBuilder();
        values.append(objDataCollector.collectObjectData(objectData));
        var tableName = objectData.getClass().getSimpleName();
        var insertQuery = new StringBuilder();
        insertQuery
                .append("INSERT ")
                .append("INTO ")
                .append(tableName).append(" ")
                .append("VALUES ")
                .append(String.format("(%s);", values));
        return insertQuery.toString();
    }

    public String createUpdateQuery(T objectData) {
        objDataCollector = new ObjectDataCollector();
        objDataCollector.collectObjectData(objectData);
        List<Field> fields = objDataCollector.getObjectFields();
        List<String> values = objDataCollector.getFieldsValues();
        var tableName = objectData.getClass().getSimpleName();
        Field annotatedField = null;
        String annotatedFieldValue = null;
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).isAnnotationPresent(Id.class)) {
                annotatedField = fields.remove(i);
                annotatedFieldValue = values.get(i);
                values.remove(i);
            }
        }
        var updateQuery = new StringBuilder().append("UPDATE ").append(tableName).append(" ").append("SET ");
        for (int i = 0; i < values.size(); i++) {
            updateQuery.append(fields.get(i).getName()).append(" = ").append(values.get(i));
        }
        removeLastCommaInString(updateQuery)
                .append(" WHERE ").append(Objects.requireNonNull(annotatedField).getName()).append(" = ")
                .append(annotatedFieldValue)
                .append(";");
        return removeLastCommaInString(updateQuery).toString();
    }

    public String createSelectQuery(T objectData) {
        objDataCollector = new ObjectDataCollector();
        objDataCollector.collectObjectData(objectData);
        List<Field> fields = objDataCollector.getObjectFields();
        List<String> values = objDataCollector.getFieldsValues();
        var tableName = objectData.getClass().getSimpleName();
        Field annotatedField = null;
        String annotatedFieldValue = null;
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).isAnnotationPresent(Id.class)) {
                annotatedField = fields.remove(i);
                annotatedFieldValue = values.get(i);
                values.remove(i);
            }
        }
        var selectQuery = new StringBuilder()
                .append("SELECT FROM ").append(tableName)
                .append(" WHERE ")
                .append(Objects.requireNonNull(annotatedField).getName())
                .append(" = ").append(annotatedFieldValue);
        return removeLastCommaInString(selectQuery).toString();
    }

    private StringBuilder removeLastCommaInString(StringBuilder updateQuery) {
        int lastCommaIndex = updateQuery.lastIndexOf(",");
        return updateQuery.deleteCharAt(lastCommaIndex);
    }
}