package ru.otus.json_writer.solution1;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Class for creating Json
 */
public class JsonCreator1 {
    private Object object;

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
        if (object == null) {
            return "null";
        }
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getType().isPrimitive() && !object.getClass().getCanonicalName().contains("Collections")) {
                return convertObjectToPrimitive(object);
            }
            if (object.getClass().getCanonicalName().contains("Collections")) {
                return object.toString().replace(" ", "");
            }
            if (object.getClass().getTypeName().equals("java.lang.String")) {
                return String.format("\"%s\"", object.toString());
            }
        }
        if (object.getClass().isArray()) {
            if (object.getClass().getComponentType().equals(byte.class)) {
                return Arrays.toString((byte[]) object).replace(" ", "");
            } else if (object.getClass().getComponentType().equals(short.class)) {
                return Arrays.toString((short[]) object).replace(" ", "");
            } else if (object.getClass().getComponentType().equals(int.class)) {
                return Arrays.toString((int[]) object).replace(" ", "");
            } else if (object.getClass().getComponentType().equals(long.class)) {
                return Arrays.toString((long[]) object).replace(" ", "");
            } else if (object.getClass().getComponentType().equals(double.class)) {
                return Arrays.toString((double[]) object).replace(" ", "");
            }
        }
        return null;
    }

    private String convertObjectToPrimitive(Object object) {
        if (object instanceof Byte) {
            return String.valueOf(((Byte) object).byteValue());
        } else if (object instanceof Short) {
            return String.valueOf(((Short) object).shortValue());
        } else if (object instanceof Long) {
            return String.valueOf(((Long) object).longValue());
        } else if (object instanceof Float) {
            return String.valueOf(((Float) object).floatValue());
        } else if (object instanceof Integer) {
            return String.valueOf(((Integer) object).intValue());
        } else if (object instanceof Double) {
            return String.valueOf(((Double) object).doubleValue());
        } else if (object instanceof Character) {
            return String.format("\"%s\"", (Character) object);
        }
        return null;
    }
}
