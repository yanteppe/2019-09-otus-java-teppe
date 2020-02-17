package ru.otus.orm.jdbc.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Parse object fields
 */
public class ObjectDataCollector {
    private List<Field> fields;
    private List<Field> annotatedFields;

    public ObjectDataCollector() {
        this.fields = new ArrayList<>();
    }

    public void parse(Object object) {
        collectFields(object);
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Field> getAnnotatedFields() {
        return annotatedFields;
    }

    public String getFieldValue(Object object, Field field) {
        field.setAccessible(true);
        Object fieldValue = null;
        try {
            fieldValue = field.get(object);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
        return Objects.requireNonNull(fieldValue).toString();
    }

    private void collectFields(Object object) {
        Collections.addAll(fields, object.getClass().getDeclaredFields());
        collectAnnotatedFields(object);
    }

    private void collectAnnotatedFields(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (isAnnotatedField(field)) {
                annotatedFields.add(field);
            }
        }
    }

    private boolean isAnnotatedField(Field field) {
        return field.getAnnotations().length > 0;
    }
}
