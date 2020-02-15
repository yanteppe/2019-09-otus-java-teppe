package ru.otus.json_writer;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.json_writer.solution1.JsonObjectWriter1;
import ru.otus.json_writer.solution2.JsonObjectWriter2;
import ru.otus.json_writer.solution2.SomeObject;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonWriterTest {
    private Gson gson = new Gson();

    @Test
    @DisplayName("Solution 1 test")
    void solution1Test() {
        System.out.println("SOLUTION 1 TEST:\n");
        var jsonWriter1 = new JsonObjectWriter1();
        System.out.println("gson - null: " + gson.toJson(null));
        System.out.println("jsonWriter1 - null: " + jsonWriter1.toJson(null) + "\n");
        System.out.println("gson - byte: " + gson.toJson((byte) 1));
        System.out.println("jsonWriter1 - byte: " + jsonWriter1.toJson((byte) 1) + "\n");
        System.out.println("gson - short: " + gson.toJson((short) 1f));
        System.out.println("jsonWriter1 - short: " + jsonWriter1.toJson((short) 1f) + "\n");
        System.out.println("gson - : " + gson.toJson(1));
        System.out.println("jsonWriter1: " + jsonWriter1.toJson(1) + "\n");
        System.out.println("gson - long: " + gson.toJson(1L));
        System.out.println("jsonWriter1 - long: " + jsonWriter1.toJson(1L) + "\n");
        System.out.println("gson - float: " + gson.toJson(1f));
        System.out.println("jsonWriter1 - float: " + jsonWriter1.toJson(1f) + "\n");
        System.out.println("gson - double: " + gson.toJson(1d));
        System.out.println("jsonWriter1 - double: " + jsonWriter1.toJson(1d) + "\n");
        System.out.println("gson - string: " + gson.toJson("aaa"));
        System.out.println("jsonWriter1 - string: " + jsonWriter1.toJson("aaa") + "\n");
        System.out.println("gson - char: " + gson.toJson('a'));
        System.out.println("jsonWriter1 - char: " + jsonWriter1.toJson('a') + "\n");
        System.out.println("gson - int array: " + gson.toJson(new int[]{1, 2, 3}));
        System.out.println("jsonWriter1 - int array: " + jsonWriter1.toJson(new int[]{1, 2, 3}) + "\n");
        System.out.println("gson - int list: " + gson.toJson(List.of(1, 2, 3)));
        System.out.println("jsonWriter1 - int list: " + jsonWriter1.toJson(List.of(1, 2, 3)) + "\n");
        System.out.println("gson - singletonList: " + gson.toJson(Collections.singletonList(1)));
        System.out.println("jsonWriter1 - singletonList: " + jsonWriter1.toJson(Collections.singletonList(1)) + "\n");

        assertEquals(gson.toJson(null), jsonWriter1.toJson(null));
        assertEquals(gson.toJson((byte) 1), jsonWriter1.toJson((byte) 1));
        assertEquals(gson.toJson((short) 1f), jsonWriter1.toJson((short) 1f));
        assertEquals(gson.toJson(1), jsonWriter1.toJson(1));
        assertEquals(gson.toJson(1L), jsonWriter1.toJson(1L));
        assertEquals(gson.toJson(1f), jsonWriter1.toJson(1f));
        assertEquals(gson.toJson(1d), jsonWriter1.toJson(1d));
        assertEquals(gson.toJson("aaa"), jsonWriter1.toJson("aaa"));
        assertEquals(gson.toJson('a'), jsonWriter1.toJson('a'));
        assertEquals(gson.toJson(new int[]{1, 2, 3}), jsonWriter1.toJson(new int[]{1, 2, 3}));
        assertEquals(gson.toJson(List.of(1, 2, 3)), jsonWriter1.toJson(List.of(1, 2, 3)));
        assertEquals(gson.toJson(Collections.singletonList(1)), jsonWriter1.toJson(Collections.singletonList(1)));
    }

    @Test
    @DisplayName("Solution 2 test")
    void solution2Test() {
        System.out.println("SOLUTION 2 TEST:\n");
        var jsonWriter2 = new JsonObjectWriter2();
        var someObject = new SomeObject();
        String myJsonResult = jsonWriter2.toJson(someObject);
        String gsonResult = gson.toJson(someObject);
        System.out.println("JsonWriter1: " + myJsonResult + "\n");
        System.out.println("Gson: " + gsonResult);
        assertEquals(gsonResult, myJsonResult);
    }
}
