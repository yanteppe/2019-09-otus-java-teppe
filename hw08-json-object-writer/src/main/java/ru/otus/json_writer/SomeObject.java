package ru.otus.json_writer;

import java.util.Arrays;
import java.util.List;

/**
 * Object for test
 */
public class SomeObject {
    private byte byteField = 1;
    private short shortField = 2;
    private long longField = 3L;
    private int intField = 4;
    private double doubleField = 5.5;
    private char charField = 'a';
    private String stringField = "some string";

    private byte byteArray[] = {1, 2, 3};
    private double doubleArray[] = {1.1, 2.2, 3.3};
    private short shortArray[] = {1, 2, 3};
    private int intArray[] = {1, 2, 3};
    private long longArray[] = {1L, 2L, 3L};
    private char charArray[] = {'a', 'b', 'c'};
    private String stringsArray[] = {"String1", "String2", "String3"};
    private Object objectsArray[] = {
            new AnotherObject("Object 1"),
            new AnotherObject("Object 2"),
            new AnotherObject("Object 3")};

    private List stringsList = Arrays.asList("value1", "value2", "value3");
    private List objectsList = Arrays.asList(
            new AnotherObject("Object 4"),
            new AnotherObject("Object 5"),
            new AnotherObject("Object 6"));
}
