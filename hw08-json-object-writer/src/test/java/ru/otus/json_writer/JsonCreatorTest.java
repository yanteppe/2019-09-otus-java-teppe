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
        assertEquals(gsonResult, myJsonResult);
    }
}
