package ru.otus.json_writer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class JsonCreator {
    private List<String> arrayTypes = Arrays.asList("byte[]", "short[]", "int[]", "long[]", "double[]");
    private StringBuilder jsonString = new StringBuilder();
    private int objectCounter = 0;
    private Object object;

    JsonCreator() {
        this.jsonString.append("{");
    }

    String toJson(Object object) {
        this.object = object;
        try {
            return createJson(object);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private String createJson(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (isArrayPrimitivesOrString(field)) {
                jsonString.append(toJsonFormat(field, field.getName(), parseArray(object, field)));
            } else if (isArrayOrListObject(object, field)) {
                jsonString.append(toJsonFormat(field, field.getName(), ""));
            } else if (isObject(field)) {
                createJson(fieldValue);
            } else {
                jsonString.append(toJsonFormat(field, field.getName(), fieldValue.toString()));
            }
        }
        String string = jsonString.append("}").toString();
        int charIndex = string.lastIndexOf(',');
        String json = string.substring(0, charIndex) + string.substring(charIndex + 1);
        return json;
    }

    private boolean isObject(Field field) {
        if (field.getType().isPrimitive()) {
            return false;
        }
        if (field.getType().getTypeName().equals("java.util.List")) {
            return false;
        }
        if (field.getType().getTypeName().equals("java.lang.String")) {
            return false;
        }
        return true;
    }

    private boolean isArrayOrListObject(Object object, Field field) {
        List listObjects = null;
        if (field.getType().getTypeName().equals("java.util.List") || field.getType().getTypeName().equals("java.lang.Object[]")) {
            try {
                Object fieldValue = field.get(object);
                if (field.getType().getTypeName().equals("java.lang.Object[]")) {
                    listObjects = getListInsteadArray((Object[]) fieldValue);
                } else {
                    listObjects = (List) fieldValue;
                }
                if (listObjects.iterator().next().getClass().isPrimitive()) {
                    return false;
                }
                if (listObjects.iterator().next().getClass().getTypeName().equals("java.lang.String") && !field.getType().getTypeName().equals("java.util.List")) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean isArrayPrimitivesOrString(Field field) {
        return field.getType().getTypeName().contains("[]")
                && !field.getType().getTypeName().contains("Object[]");
    }


    private String toJsonFormat(Field field, String type, String value) throws IllegalAccessException {
        if (field.getType().isPrimitive() && !field.getType().getTypeName().equals("char")) {
            return createPrimitive(type, value);
        } else if (!field.getType().isArray() && field.getType().getTypeName().contains("char")) {
            return createString(type, value);
        } else if (!field.getType().isArray() && field.getType().getTypeName().contains("String")) {
            return createString(type, value);
        } else if (field.getType().isArray() && field.getType().getTypeName().contains("String")) {
            return createStringsArray(type, value);
        } else if (field.getType().isArray() && arrayTypes.contains(field.getType().getTypeName())) {
            return createPrimitivesArray(type, value);
        } else if (field.getType().getTypeName().contains("char[]")) {
            return createStringsArray(type, value);
        } else if (field.getType().getTypeName().contains("List") || field.getType().getTypeName().contains("Object[]")) {
            return createListObjects(field);
        }
        return null;
    }

    private String createListObjects(Field field) throws IllegalAccessException {
        StringBuilder json = new StringBuilder();
        field.setAccessible(true);
        Object fieldValue = field.get(object);
        List objects = null;
        if (field.getType().getTypeName().equals("java.lang.Object[]")) {
            objects = getListInsteadArray((Object[]) fieldValue);
        } else {
            objects = (List) fieldValue;
        }
        if (objects.iterator().next().getClass().getTypeName().equals("java.lang.String")) {
            return createStringsArray(field.getName(), Arrays.toString(objects.toArray()));
        }
        for (Object object : objects) {
            objectCounter++;
            if (objectCounter != objects.size()) {
                json.append(parseObjectToJson(object)).append(",");
            } else {
                json.append(parseObjectToJson(object));
            }
        }
        objectCounter = 0;
        return String.format("\"%s\":[%s],", field.getName(), json);
    }

    private String createPrimitive(String type, String value) {
        String editType = String.format("\"%s\":", type);
        String editValue = editValueToPrimitive(value + ",");
        return editType + editValue;
    }

    private String createStringsArray(String type, String value) {
        List<String> temporary = new ArrayList<>();
        String[] subString;
        String delimeter = ",";
        subString = value.split(delimeter);
        for (String str : subString) {
            String string = str.replace("[", "").replace("]", "").trim();
            String editString = String.format("\"%s\"", string);
            temporary.add(editString);
        }
        String result = Arrays.toString(temporary.toArray()).replace(" ", "") + ",";
        jsonString.append(String.format("\"%s\":", type));
        return result;
    }

    private String createString(String type, String value) {
        String editType = String.format("\"%s\":", type);
        String editValue = editValueToString(value) + ",";
        return editType + editValue;
    }

    private String createPrimitivesArray(String type, String value) {
        String editType = String.format("\"%s\":", type);
        String editValue = editValueToString(value) + ",";
        return editType + editValue;
    }

    private String editValueToPrimitive(String value) {
        return value.replace("\"", "").replace(" ", "");
    }

    private String editValueToString(String value) {
        return String.format("\"%s\"", value);
    }

    private String parseObjectToJson(Object object) throws IllegalAccessException {
        StringBuilder string = new StringBuilder();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            string.append(toJsonFormat(field, field.getName(), fieldValue.toString()));
        }
        return String.format("{%s}", removeCharInString(String.valueOf(string), string.length() - 1));
    }

    private String parseArray(Object object, Field field) {
        try {
            Object fieldValue = field.get(object);
            switch (field.getGenericType().getTypeName()) {
                case "byte[]":
                    return Arrays.toString((byte[]) fieldValue);
                case "short[]":
                    return Arrays.toString((short[]) fieldValue);
                case "int[]":
                    return Arrays.toString((int[]) fieldValue);
                case "long[]":
                    return Arrays.toString((long[]) fieldValue);
                case "double[]":
                    return Arrays.toString((double[]) fieldValue);
                case "char[]":
                    return Arrays.toString((char[]) fieldValue);
                case "java.lang.String[]":
                    return Arrays.toString((String[]) fieldValue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String removeCharInString(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    private List getListInsteadArray(Object[] objects) {
        List result = new ArrayList();
        Collections.addAll(result, objects);
        return result;
    }
}
