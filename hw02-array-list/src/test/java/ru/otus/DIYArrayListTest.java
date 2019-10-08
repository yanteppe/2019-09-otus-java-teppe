package ru.otus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DIYArrayList class
 *
 * @see DIYArrayList
 */
@DisplayName("Testing the DIYArrayList class")
public class DIYArrayListTest {
    private List<String> listStrings;
    private List<Integer> listIntegers;

    @BeforeEach()
    public void preconditions() {
        listStrings = Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "X", "W", "V", "U", "T", "R", "Q", "P", "O", "N", "M", "Y");
        listIntegers = Arrays.asList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13);
    }

    @AfterEach()
    public void postconditions() {
        listIntegers = null;
        listStrings = null;
    }

    @Test()
    @DisplayName("Test method: size()")
    public void testSizeMethod() {
        List actualList = generateTestList(listStrings);
        int expectedSize = 24;
        assertEquals(expectedSize, actualList.size(),
                String.format("List 'listStrings' size is: %s, but expected: %s", actualList.size(), expectedSize));
    }

    @Test()
    @DisplayName("Test method: add() - Strings")
    public void testAddMethodWithStrings() {
        List actualList = generateTestList(listStrings);
        assertEquals(listStrings, actualList, "List 'listStrings' not equal 'actualList' list");
    }

    @Test()
    @DisplayName("Test method: add() - Int")
    public void testAddMethodWithInt() {
        List actualList = generateTestList(listIntegers);
        assertEquals(listIntegers, actualList, "List 'listIntegers' not equal 'actualList' list");
    }

    @Test()
    @DisplayName("Test method: add(int index, T element)")
    public void testAddMethodWithIndex() {
        List<Object> actualList = generateTestList(listStrings);
        String expectedResult = "TEST";
        actualList.add(3, expectedResult);
        assertEquals(expectedResult, actualList.get(3), String.format("List 'listStrings' by index 3 not contains value '%s'", expectedResult));
        assertEquals(actualList.size(), generateTestList(listStrings).size() + 1, "The 'listStrings' list did not increase after inserting a new item");
    }

    @Test()
    @DisplayName("Test method: addAll(Collection<? extends T> c)")
    public void testAddAll() {
        List<Object> actualList = generateTestList(listStrings);
        List<String> newList = Arrays.asList("test1", "test2", "test3");
        actualList.addAll(newList);
        assertTrue(actualList.contains(newList.get(0)), "List 'listStrings' does not contain an element under index 0 from list 'newList'");
        assertTrue(actualList.contains(newList.get(1)), "List 'listStrings' does not contain an element under index 1 from list 'newList'");
        assertTrue(actualList.contains(newList.get(2)), "List 'listStrings' does not contain an element under index 2 from list 'newList'");
    }

    @Test()
    @DisplayName("Test method: set(int index, T element)")
    public void testSet() {
        List<Object> actualList = generateTestList(listStrings);
        String expectedResult = "TEST";
        actualList.set(3, expectedResult);
        assertEquals(expectedResult, actualList.get(3), String.format("List 'listStrings' by index 3 not contains value '%s'", expectedResult));
        assertEquals(actualList.size(), generateTestList(listStrings).size(), "List 'listStrings' should not grow after set a new element");
    }

    @Test()
    @DisplayName("Test method: remove(int index)")
    public void testRemove() {
        List actualList = generateTestList(listStrings);
        Object removedElement = actualList.get(6);
        actualList.remove(6);
        assertTrue(!actualList.contains(removedElement), "List 'listStrings' contains removed element, expected: not contains");
        assertEquals(actualList.size(), generateTestList(listStrings).size() - 1,
                String.format("List 'listStrings' size is: %s, but expected: %s, " +
                        "the list should be reduced after removing the element", actualList.size(), actualList.size() - 1));
    }

    @Test()
    @DisplayName("Test method: get(int index)")
    public void testGetMethod() {
        List actualList = generateTestList(listStrings);
        String expectedElement = "N";
        assertEquals(expectedElement, actualList.get(21),
                String.format("Actual element: '%s', but expected: '%s'", actualList.get(21), expectedElement));
    }

    @Test()
    @DisplayName("Test method: clear()")
    public void testClearMethod() {
        List actualList = generateTestList(listStrings);
        actualList.clear();
        assertEquals(0, actualList.size(), "List 'listStrings' not cleared");
        assertTrue(actualList.isEmpty(), "List 'listStrings' not cleared");
    }

    @Test()
    @DisplayName("Test method: isEmpty()")
    public void testIsEmptyMethod() {
        List actualList = generateTestList(listStrings);
        assertFalse(actualList.isEmpty(), "Method isEmpty should be: false");
        actualList.clear();
        assertTrue(actualList.isEmpty(), "Method isEmpty should be: true");
    }

    @Test()
    @DisplayName("Test method: contains(Object object)")
    public void testContainsMethod() {
        List actualList = generateTestList(listStrings);
        Object expectedElement = "A";
        assertTrue(actualList.contains(expectedElement),
                String.format("List 'listStrings' not contains element: '%s', expected: contains", expectedElement));
        expectedElement = "TEST";
        assertFalse(actualList.contains(expectedElement),
                String.format("List 'listStrings' contains element: '%s', expected: not contains", expectedElement));
    }

    /**
     * Helper method - generate list for tests
     *
     * @param typeOfList test list template
     * @return List
     */
    private List<Object> generateTestList(List typeOfList) {
        List<Object> newList = new DIYArrayList<>();
        for (Object element : typeOfList) {
            newList.add(element);
        }
        return newList;
    }
}
