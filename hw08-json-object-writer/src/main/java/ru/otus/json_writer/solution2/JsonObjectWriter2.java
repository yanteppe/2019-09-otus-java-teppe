package ru.otus.json_writer.solution2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JsonObjectWriter2 {
    private List<String> arrayTypes = Arrays.asList("byte[]", "short[]", "int[]", "long[]", "double[]");
    private StringBuilder jsonString = new StringBuilder();
    private int objectCounter = 0;
    private Object object;

    public JsonObjectWriter2() {
        this.jsonString.append("{");
    }

    /**
     * Create json from object
     *
     * @param object some object
     * @return Json string
     */
    public String toJson(Object object) {
        this.object = object;
        try {
            return createJson(object);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * Parsing object and creating a Json string
     *
     * @param object object for Json
     * @return Json string
     * @throws IllegalAccessException IllegalAccessException
     */
    private String createJson(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (isArrayPrimitivesOrStrings(field)) {
                jsonString.append(convertToString(field, field.getName(), selectArrayType(object, field)));
            } else if (isArrayOrListObjects(object, field)) {
                jsonString.append(convertToString(field, field.getName(), ""));
            } else if (isObject(field)) {
                createJson(fieldValue);
            } else {
                jsonString.append(convertToString(field, field.getName(), fieldValue.toString()));
            }
        }
        String string = jsonString.append("}").toString();
        int charIndex = string.lastIndexOf(',');
        return string.substring(0, charIndex) + string.substring(charIndex + 1);
    }

    /**
     * Check object field is Object or not
     *
     * @param field object field
     * @return boolean
     */
    private boolean isObject(Field field) {
        return field.getGenericType().equals(field.getDeclaringClass());
    }

    /**
     * Check object field is array or list
     *
     * @param object object for Json
     * @return boolean
     */
    private boolean isArrayOrListObjects(Object object, Field field) {
        List listObjects = null;
        if (object.getClass().getCanonicalName().contains("Collections") || field.getType().getTypeName().equals("java.lang.Object[]")) {
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
                if (listObjects.iterator().next().getClass().getTypeName().equals("java.lang.String")
                        && !field.getType().getTypeName().equals("java.util.List")) {
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

    /**
     * Check object field is primitives array or strings array
     *
     * @param field object field
     * @return boolean
     */
    private boolean isArrayPrimitivesOrStrings(Field field) {
        return field.getType().isArray() && !field.getType().getTypeName().contains("Object[]");
    }

    /**
     * Convert object field to string
     *
     * @param type  type field
     * @param value value field
     * @return String
     * @throws IllegalAccessException IllegalAccessException
     */
    private String convertToString(Field field, String type, String value) throws IllegalAccessException {
        if (field.getType().isPrimitive() && !field.getType().getTypeName().equals("char")) {
            return convertPrimitivesToString(type, value);
        } else if (!field.getType().isArray() && field.getType().getTypeName().contains("char")) {
            return createString(type, value);
        } else if (!field.getType().isArray() && field.getType().getTypeName().contains("String")) {
            return createString(type, value);
        } else if (field.getType().isArray() && field.getType().getTypeName().contains("String")) {
            return convertStringsArrayToString(type, value);
        } else if (field.getType().isArray() && arrayTypes.contains(field.getType().getTypeName())) {
            return convertPrimitivesArrayToString(type, value);
        } else if (field.getType().getTypeName().contains("char[]")) {
            return convertStringsArrayToString(type, value);
        } else if (field.getType().getTypeName().contains("List") || field.getType().getTypeName().contains("Object[]")) {
            return convertListObjectsToString(field);
        }
        return null;
    }

    /**
     * Convert list objects to string
     *
     * @param field object field
     * @return string
     * @throws IllegalAccessException IllegalAccessException
     */
    private String convertListObjectsToString(Field field) throws IllegalAccessException {
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
            return convertStringsArrayToString(field.getName(), Arrays.toString(objects.toArray()));
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

    /**
     * Convert primitives types to string
     *
     * @param type  object field type
     * @param value value field
     * @return string
     */
    private String convertPrimitivesToString(String type, String value) {
        String editType = String.format("\"%s\":", type);
        String editValue = convertValueToPrimitive(value + ",");
        return editType + editValue;
    }

    /**
     * Convert strings array to string
     *
     * @param type  object field type
     * @param value value field
     * @return string
     */
    private String convertStringsArrayToString(String type, String value) {
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

    /**
     * Convert primitives array to string
     *
     * @param type  object field type
     * @param value value field
     * @return string
     */
    private String convertPrimitivesArrayToString(String type, String value) {
        String editType = String.format("\"%s\":", type);
        String editValue = convertValueToString(value)
                .replace("\"", "")
                .replace(" ", "") + ",";
        return editType + editValue;
    }

    private String createString(String type, String value) {
        String editType = String.format("\"%s\":", type);
        String editValue = convertValueToString(value) + ",";
        return editType + editValue;
    }

    private String convertValueToPrimitive(String value) {
        return value.replace("\"", "").replace(" ", "");
    }

    private String convertValueToString(String value) {
        return String.format("\"%s\"", value);
    }

    private String parseObjectToJson(Object object) throws IllegalAccessException {
        StringBuilder string = new StringBuilder();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            string.append(convertToString(field, field.getName(), fieldValue.toString()));
        }
        return String.format("{%s}", removeCharInString(String.valueOf(string), string.length() - 1));
    }

    private String selectArrayType(Object object, Field field) {
        try {
            Object fieldValue = field.get(object);
            if (fieldValue.getClass().getComponentType().equals(byte.class)) {
                return Arrays.toString((byte[]) fieldValue);
            } else if (fieldValue.getClass().getComponentType().equals(short.class)) {
                return Arrays.toString((short[]) fieldValue);
            } else if (fieldValue.getClass().getComponentType().equals(int.class)) {
                return Arrays.toString((int[]) fieldValue);
            } else if (fieldValue.getClass().getComponentType().equals(long.class)) {
                return Arrays.toString((long[]) fieldValue);
            } else if (fieldValue.getClass().getComponentType().equals(double.class)) {
                return Arrays.toString((double[]) fieldValue);
            } else if (fieldValue.getClass().getComponentType().equals(char.class)) {
                return Arrays.toString((char[]) fieldValue);
            } else if (fieldValue.getClass().getComponentType().equals(String.class)) {
                return Arrays.toString((String[]) fieldValue);
            }
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
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