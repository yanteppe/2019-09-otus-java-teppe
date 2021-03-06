package ru.otus.testframework;

import ru.otus.testframework.annotation.After;
import ru.otus.testframework.annotation.Before;
import ru.otus.testframework.annotation.Test;

public class TestMethods {

    @Before
    void beforeMethod() {
        System.out.println("\n--- Test ---");
        System.out.println("Before test");
    }

    @Test
    void testMethod() {
        System.out.println("Test method");
    }

    @Test
    void testMethodWithException() {
        System.out.println("Test method with exception");
        throw new RuntimeException();
    }

    @After
    void afterMethod() {
        System.out.println("After test");
    }
}
