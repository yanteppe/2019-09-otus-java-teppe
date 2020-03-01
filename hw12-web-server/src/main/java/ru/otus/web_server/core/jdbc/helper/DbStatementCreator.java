package ru.otus.web_server.core.jdbc.helper;


import ru.otus.web_server.core.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Database Statement Creator
 *
 * @param <T> Some object to save to the database
 */
public class DbStatementCreator<T> {
    private ObjectDataCollector objectDataCollector;
    StringJoiner values;
    List<String> listValues;

    public List<String> getListValues() {
        List<String> formattedValues = new ArrayList<>();
        for (String value : listValues) {
            formattedValues.add(value.replace(",", ""));
        }
        return formattedValues;
    }

    public String createInsertStatement(T objectData) {
        checkForNull(objectData);
        objectDataCollector = new ObjectDataCollector();
        objectDataCollector.collectObjectData(objectData);
        List<Field> fields = objectDataCollector.getObjectFields();
        listValues = objectDataCollector.getFieldsValues();
        var tableName = objectData.getClass().getSimpleName();
        var insertStatement = new StringBuilder();
        // delete item with Id annotation
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).isAnnotationPresent(Id.class)) {
                fields.remove(i);
                listValues.remove(i);
            }
        }
        // create string values
        values = new StringJoiner(",");
        for (String value : listValues) {
            values.add("?");
        }
        // create insert statement
        insertStatement
                .append("INSERT ")
                .append("INTO ")
                .append(tableName)
                .append("(");
        var fieldsNames = new StringJoiner(",");
        for (Field field : fields) {
            fieldsNames.add(field.getName());
        }
        insertStatement
                .append(fieldsNames)
                .append(")")
                .append(" VALUES ")
                .append("(")
                .append(values)
                .append(")");
        return insertStatement.toString();
    }

    public String createSelectByIdStatement(Class<T> clazz) {
        checkForNull(clazz);
        Field[] fields = clazz.getDeclaredFields();
        var tableName = clazz.getSimpleName();
        String annotatedField = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                annotatedField = field.getName();
            }
        }
        var selectStatement = new StringBuilder()
                .append("SELECT * FROM ").append(tableName)
                .append(" WHERE ")
                .append(annotatedField)
                .append(" = ").append("?");
        return selectStatement.toString();
    }

    public String createSelectByParamStatement(Class<T> clazz, String columnName) {
        checkForNull(clazz);
        var tableName = clazz.getSimpleName();
        var selectStatement = new StringBuilder()
              .append("SELECT * FROM ").append(tableName)
              .append(" WHERE ")
              .append(columnName)
              .append(" = ").append("?");
        return selectStatement.toString();
    }

    public String createSelectAllStatement(Class<T> clazz) {
        checkForNull(clazz);
        return new StringBuilder().append("SELECT * FROM ").append(clazz.getSimpleName()).toString();
    }

    private <T> void checkForNull(T object) {
        if (object == null) throw new IllegalArgumentException("Parameter must not be equal null");
    }

    public String createUpdateStatement(T objectData) {
        checkForNull(objectData);
        objectDataCollector = new ObjectDataCollector();
        objectDataCollector.collectObjectData(objectData);
        List<Field> fields = objectDataCollector.getObjectFields();
        listValues = objectDataCollector.getFieldsValues();
        var tableName = objectData.getClass().getSimpleName();
        Field annotatedField = null;
        String annotatedFieldValue = null;
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).isAnnotationPresent(Id.class)) {
                annotatedField = fields.remove(i);
                annotatedFieldValue = listValues.get(i);
                listValues.remove(i);
            }
        }
        var updateStatement = new StringBuilder().append("UPDATE ").append(tableName).append(" ").append("SET ");
        var fieldsNames = new StringJoiner(",");
        for (int i = 0; i < listValues.size(); i++) {
            fieldsNames.add(fields.get(i).getName() + "= " + "?");
        }
        updateStatement
                .append(fieldsNames)
                .append(" WHERE ").append(Objects.requireNonNull(annotatedField).getName()).append(" = ")
                .append(annotatedFieldValue)
                .append(";");
        return updateStatement.deleteCharAt(updateStatement.lastIndexOf(",")).toString();
    }
}