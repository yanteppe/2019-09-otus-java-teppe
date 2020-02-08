package ru.otus.json_writer;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonCreatorTest {

    @Test
    void objectToJsonTest() {
        var someObject = new SomeObject();
        String myJsonResult = new JsonCreator().toJson(someObject);
        String gsonResult = new Gson().toJson(someObject);
        System.out.println("MyJson: " + myJsonResult + "\n");
        System.out.println("Gson: " + gsonResult);
        assertEquals(gsonResult, myJsonResult);
    }
}
