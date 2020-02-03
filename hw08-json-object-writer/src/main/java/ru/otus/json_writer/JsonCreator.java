package ru.otus.json_writer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonCreator {
    private List<String> arrayTypes = Arrays.asList("byte[]", "short[]", "int[]", "long[]", "double[]", "char[]");
    private StringBuilder jsonString = new StringBuilder();

    public String createJsonString(Field field, String type, String value) {
        if (field.getType().isPrimitive()) {
            return createPrimitive(type, value);
        } else if (!field.getType().isArray() && field.getType().getTypeName().contains("String")) {
            return createString(type, value);
        } else if (field.getType().isArray() && field.getType().getTypeName().contains("String")) {
            return createStringsArray(type, value);
        } else if (field.getType().isArray() && arrayTypes.contains(field.getType().getTypeName())) {
            return createPrimitivesArray(type, value);
        } else if (field.getType().getTypeName().contains("List")) {
            return createListObjects(type);
        }
        return null;
    }

    private String createListObjects(String type) {
        return String.format("\"%s\":[", type);
    }

    private String createPrimitive(String type, String value) {
        String editType = String.format("\"%s\":", type);
        String editValue = editValueToPrimitive(value + ",");
//        jsonString.append(String.format("\"%s\":", type));
//        jsonString.append(editValueToPrimitive(value + ","));
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
        String result = Arrays.toString(temporary.toArray()).replace(" ", "");
        jsonString.append(String.format("\"%s\":", type));
        jsonString.append(result);
        return String.valueOf(jsonString.append(","));
    }

    private String createString(String type, String value) {
        String editType = String.format("\"%s\":", type);
        String editValue = editValueToString(value) + ",";
//        jsonString.append(String.format("\"%s\":", type));
//        jsonString.append(editValueToString(value) + ",");
        return editType + editValue;
    }

    private String createPrimitivesArray(String type, String value) {
        jsonString.append(String.format("\"%s\":", type));
        jsonString.append(value + ",");
        return String.valueOf(jsonString);
    }

    private String editValueToPrimitive(String value) {
        return value.replace("\"", "").replace(" ", "");
    }

    private String editValueToString(String value) {
        return String.format("\"%s\"", value);
    }

    public StringBuilder getJsonString() {
        return jsonString;
    }
}
