package ru.otus.hw;

import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

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
