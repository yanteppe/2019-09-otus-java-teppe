package ru.otus.json_writer;

import java.lang.reflect.Field;
import java.util.*;

public class JsonWriterDemo {
    private StringBuilder jsonString = new StringBuilder();
    private StringBuilder objectField = new StringBuilder();
    private JsonCreator jsonCreator = new JsonCreator();
    int objectCounter = 0;
    String fields = null;
    //private Map<Object, Object> fields = new LinkedHashMap<>();

    public static void main(String[] args) {
        var someObject = new SomeObject();
        try {
            new JsonWriterDemo().collectObjectFields(someObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    public static JsonObject createJson1(Object object) {
//        var jsonObject = Json.createObjectBuilder()
//                .add("firstName", "Duke")
//                .add("age", 28)
//                .add("streetAddress", "100 Internet Dr")
//                .add("phoneNumbers",
//                        Json.createArrayBuilder()
//                                .add(Json.createObjectBuilder()
//                                        .add("type", "home")
//                                        .add("number", "222-222-2222")))
//                .build();
//        System.out.println("Json object: " + jsonObject + "\n");
//        return jsonObject;
//    }

    private void collectObjectFields(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (isArray(field)) {
                jsonCreator.createJsonString(field, field.getName(), parseArray(object, field));
            } else if (isListObject(object, field)) {
                List objects = (List) fieldValue;
                jsonCreator.getJsonString()
                        .append(jsonCreator.createJsonString(field, field.getName(), fieldValue.toString()));
                if (!jsonCreator.getJsonString().toString().contains("[")) {
                    jsonCreator.getJsonString().append("[");
                }
                for (Object obj : objects) {
                    objectCounter++;
                    if (objectCounter != objects.size()) {
                        jsonCreator.getJsonString().append(parseObjectToJson(obj) + ",");
                    } else {
                        jsonCreator.getJsonString().append(parseObjectToJson(obj));
                        jsonCreator.getJsonString().append("]");
                    }
                }
            } else if (isObject(field)) {
                collectObjectFields(fieldValue);
            } else {
                jsonCreator.getJsonString().append(jsonCreator.createJsonString(field, field.getName(), fieldValue.toString()));
            }
        }
        System.out.println("JSON: " + jsonCreator.getJsonString());
    }

    private String parseObjectToJson(Object object) throws IllegalAccessException {
        var jsonCreator = new JsonCreator();
        StringBuilder string = new StringBuilder();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            string.append(jsonCreator.createJsonString(field, field.getName(), fieldValue.toString()));
        }
        return String.format("{%s}", removeCharInString(String.valueOf(string), string.length() - 1));
    }

    private static String removeCharInString(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

//    private void collectObjectFields(Object object) throws IllegalAccessException {
//        int id = new Random().nextInt(99);
//        for (Field field : object.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            Object fieldValue = field.get(object);
//            if (isArray(field)) {
//                fieldValue = parseArray(object, field);
//                fields.put(getFieldNameWithClassName(id, field), fieldValue);
//            } else if (isListObject(object, field)) {
//                List objects = (List) fieldValue;
//                for (Object obj : objects) {
//                    collectObjectFields(obj);
//                }
//            } else if (isObject(field)) {
//                collectObjectFields(fieldValue);
//            } else {
//                fields.put(getFieldNameWithClassName(id, field), fieldValue);
//            }
//        }
//        for (Map.Entry<Object, Object>  entry: fields.entrySet()) {
//            System.out.println(entry.getKey() + " = " + entry.getValue());
//        }
//    }

    private String getFieldNameWithClassName(int counter, Field field) {
        String fullClassName = field.getDeclaringClass().getCanonicalName();
        return counter + fullClassName.substring(fullClassName.lastIndexOf('.') + 1) + "." + field.getName();
    }

    private boolean isListObject(Object object, Field field) {
        if (field.getGenericType().getTypeName().equals("java.util.List")) {
            try {
                Object fieldValue = field.get(object);
                List objects = (List) fieldValue;
                if (objects.iterator().next().getClass().isPrimitive()) {
                    return false;
                }
                if (objects.iterator().next().getClass().getTypeName().equals("java.lang.String")) {
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

    private boolean isArray(Field field) {
        if (field.getGenericType().getTypeName().contains("[]")) {
            return true;
        } else {
            return false;
        }
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
}
