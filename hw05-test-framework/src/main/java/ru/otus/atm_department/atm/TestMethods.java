package ru.otus.atm_department.atm;

import ru.otus.atm_department.atm.annotation.After;
import ru.otus.atm_department.atm.annotation.Before;
import ru.otus.atm_department.atm.annotation.Test;

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
