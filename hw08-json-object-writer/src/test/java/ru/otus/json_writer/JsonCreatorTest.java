package ru.otus.json_writer;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.json_writer.solution1.JsonCreator1;
import ru.otus.json_writer.solution2.JsonCreator2;
import ru.otus.json_writer.solution2.SomeObject;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonCreatorTest {
    private Gson gson;

    @BeforeEach
    void preconditions() {
        gson = new Gson();
    }

    @Test
    @DisplayName("Solution 1 test")
    void solution1Test() {
        System.out.println("SOLUTION 1 TEST:\n");
        var jsonCreator1 = new JsonCreator1();
        System.out.println("gson - null: " + gson.toJson(null));
        System.out.println("jsonCreator1 - null: " + jsonCreator1.toJson(null) + "\n");
        System.out.println("gson - byte: " + gson.toJson((byte) 1));
        System.out.println("jsonCreator1 - byte: " + jsonCreator1.toJson((byte) 1) + "\n");
        System.out.println("gson - short: " + gson.toJson((short) 1f));
        System.out.println("jsonCreator1 - short: " + jsonCreator1.toJson((short) 1f) + "\n");
        System.out.println("gson - : " + gson.toJson(1));
        System.out.println("jsonCreator1: " + jsonCreator1.toJson(1) + "\n");
        System.out.println("gson - long: " + gson.toJson(1L));
        System.out.println("jsonCreator1 - long: " + jsonCreator1.toJson(1L) + "\n");
        System.out.println("gson - float: " + gson.toJson(1f));
        System.out.println("jsonCreator1 - float: " + jsonCreator1.toJson(1f) + "\n");
        System.out.println("gson - double: " + gson.toJson(1d));
        System.out.println("jsonCreator1 - double: " + jsonCreator1.toJson(1d) + "\n");
        System.out.println("gson - string: " + gson.toJson("aaa"));
        System.out.println("jsonCreator1 - string: " + jsonCreator1.toJson("aaa") + "\n");
        System.out.println("gson - char: " + gson.toJson('a'));
        System.out.println("jsonCreator1 - char: " + jsonCreator1.toJson('a') + "\n");
        System.out.println("gson - int array: " + gson.toJson(new int[]{1, 2, 3}));
        System.out.println("jsonCreator1 - int array: " + jsonCreator1.toJson(new int[]{1, 2, 3}) + "\n");
        System.out.println("gson - int list: " + gson.toJson(List.of(1, 2, 3)));
        System.out.println("jsonCreator1 - int list: " + jsonCreator1.toJson(List.of(1, 2, 3)) + "\n");
        System.out.println("gson - singletonList: " + gson.toJson(Collections.singletonList(1)));
        System.out.println("jsonCreator1 - singletonList: " + jsonCreator1.toJson(Collections.singletonList(1)) + "\n");

        assertEquals(gson.toJson(null), jsonCreator1.toJson(null));
        assertEquals(gson.toJson((byte) 1), jsonCreator1.toJson((byte) 1));
        assertEquals(gson.toJson((short) 1f), jsonCreator1.toJson((short) 1f));
        assertEquals(gson.toJson(1), jsonCreator1.toJson(1));
        assertEquals(gson.toJson(1L), jsonCreator1.toJson(1L));
        assertEquals(gson.toJson(1f), jsonCreator1.toJson(1f));
        assertEquals(gson.toJson(1d), jsonCreator1.toJson(1d));
        assertEquals(gson.toJson("aaa"), jsonCreator1.toJson("aaa"));
        assertEquals(gson.toJson('a'), jsonCreator1.toJson('a'));
        assertEquals(gson.toJson(new int[]{1, 2, 3}), jsonCreator1.toJson(new int[]{1, 2, 3}));
        assertEquals(gson.toJson(List.of(1, 2, 3)), jsonCreator1.toJson(List.of(1, 2, 3)));
        assertEquals(gson.toJson(Collections.singletonList(1)), jsonCreator1.toJson(Collections.singletonList(1)));
    }

    @Test
    @DisplayName("Solution 2 test")
    void solution2Test() {
        System.out.println("SOLUTION 2 TEST:\n");
        var jsonCreator2 = new JsonCreator2();
        var someObject = new SomeObject();
        String myJsonResult = jsonCreator2.toJson(someObject);
        String gsonResult = gson.toJson(someObject);
        System.out.println("MyJson: " + myJsonResult + "\n");
        System.out.println("Gson: " + gsonResult);
        assertEquals(gsonResult, myJsonResult);
    }
}
