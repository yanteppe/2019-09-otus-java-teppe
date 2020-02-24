package ru.otus.orm.jdbc.helper;


import ru.otus.orm.core.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Database Statement Creator
 *
 * @param <T> Some object to save to the database
 */
public class DbStatementCreator<T> {
    private ObjectDataCollector objectDataCollector;
    StringBuilder stringValues;
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
//        List<String> listValues = objectDataCollector.getFieldsValues();
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
        stringValues = new StringBuilder();
        for (String value : listValues) {
            stringValues.append("?").append(",");
//            stringValues.append(value);
        }
        removeLastCommaInString(stringValues);
        // create insert statement
        insertStatement
                .append("INSERT ")
                .append("INTO ")
                .append(tableName)
                .append("(");
        for (Field field : fields) {
            insertStatement.append(field.getName()).append(",");
        }
        removeLastCommaInString(insertStatement)
                .append(")")
                .append(" VALUES ")
                .append("(")
                .append(stringValues)
                .append(")");
        return insertStatement.toString();
    }

    public String createSelectStatement(Class<T> clazz) {
        checkForNull(clazz);
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
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
        for (int i = 0; i < listValues.size(); i++) {
            updateStatement.append(fields.get(i).getName()).append(" = ").append("?, ");
        }
        removeLastCommaInString(updateStatement)
                .append(" WHERE ").append(Objects.requireNonNull(annotatedField).getName()).append(" = ")
                .append(annotatedFieldValue)
                .append(";");
        return removeLastCommaInString(updateStatement).toString();
    }

    private StringBuilder removeLastCommaInString(StringBuilder statement) {
        int lastCommaIndex = statement.lastIndexOf(",");
        return statement.deleteCharAt(lastCommaIndex);
    }
}