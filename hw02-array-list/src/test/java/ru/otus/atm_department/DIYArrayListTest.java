package ru.otus.atm_department;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DIYArrayList class
 *
 * @see DIYArrayList
 */
@DisplayName("Testing the DIYArrayList class")
public class DIYArrayListTest {
    private List<String> stringsList;
    private List<Integer> integersList;

    @BeforeEach()
    public void preconditions() {
        stringsList = Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "X", "W", "V", "U", "T", "S", "R", "Q", "P", "O", "N", "M", "Z");
        integersList = Arrays.asList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 25);
    }

    @AfterEach()
    public void postconditions() {
        integersList = null;
        stringsList = null;
    }

    @Test()
    @DisplayName("Test method: size()")
    public void testSizeMethod() {
        List actualList = generateTestList(stringsList);
        int expectedSize = 25;
        System.out.println("actual list: " + actualList.toString());
        System.out.println("actual list size: " + actualList.size());
        assertEquals(expectedSize, actualList.size(),
                String.format("List 'stringsList' size is: %s, but expected: %s", actualList.size(), expectedSize));
    }

    @Test()
    @DisplayName("Test method: add() - Strings")
    public void testAddMethodWithStrings() {
        List actualList = generateTestList(stringsList);
        System.out.println("expected list: " + stringsList.toString());
        System.out.println("actual list:   " + actualList.toString());
        assertArrayEquals(stringsList.toArray(), actualList.toArray(), "List 'stringsList' not equal 'actualList' list");
    }

    @Test()
    @DisplayName("Test method: add() - Int")
    public void testAddMethodWithInt() {
        List actualList = generateTestList(integersList);
        System.out.println("expected list: " + integersList.toString());
        System.out.println("actual list:   " + actualList.toString());
        assertArrayEquals(integersList.toArray(), actualList.toArray(), "List 'integersList' not equal 'actualList' list");
    }

    @Test()
    @DisplayName("Test method: add(int index, T element)")
    public void testAddMethodWithIndex() {
        List<Object> actualList = generateTestList(stringsList);
        String expectedResult = "TEST";
        actualList.add(25, expectedResult);
        assertEquals(expectedResult, actualList.get(25), String.format("List 'stringsList' by index 3 not contains value '%s'", expectedResult));
        assertEquals(generateTestList(stringsList).size() + 1, actualList.size(), "The 'stringsList' list did not increase after inserting a new item");
    }

    @Test()
    @DisplayName("Test method: addAll(Collection<? extends T> c)")
    public void testAddAll() {
        List<Object> actualList = generateTestList(stringsList);
        List<String> newList = Arrays.asList("test1", "test2", "test3");
        actualList.addAll(newList);
        System.out.println("actual list: " + actualList.toString());
        assertTrue(actualList.contains(newList.get(0)), "List 'stringsList' does not contain an element under index 0 from list 'newList'");
        assertTrue(actualList.contains(newList.get(1)), "List 'stringsList' does not contain an element under index 1 from list 'newList'");
        assertTrue(actualList.contains(newList.get(2)), "List 'stringsList' does not contain an element under index 2 from list 'newList'");
    }

    @Test()
    @DisplayName("Test method: set(int index, T element)")
    public void testSet() {
        List<Object> actualList = generateTestList(stringsList);
        String expectedResult = "TEST";
        actualList.set(3, expectedResult);
        assertEquals(expectedResult, actualList.get(3), String.format("List 'stringsList' by index 3 not contains value '%s'", expectedResult));
        assertEquals(actualList.size(), generateTestList(stringsList).size(), "List 'stringsList' should not grow after set a new element");
    }

    @Test()
    @DisplayName("Test method: remove(int index)")
    public void testRemove() {
        List actualList = generateTestList(stringsList);
        Object removedElement = actualList.get(6);
        actualList.remove(6);
        assertTrue(!actualList.contains(removedElement), "List 'stringsList' contains removed element, expected: not contains");
        assertEquals(generateTestList(stringsList).size() - 1, actualList.size(),
                String.format("List 'stringsList' size is: %s, but expected: %s, " +
                        "the list should be reduced after removing the element", actualList.size(), actualList.size() - 1));
    }

    @Test()
    @DisplayName("Test method: get(int index)")
    public void testGetMethod() {
        List actualList = generateTestList(stringsList);
        String expectedElement = "C";
        assertEquals(expectedElement, actualList.get(2),
                String.format("Actual element: '%s', but expected: '%s'", actualList.get(2), expectedElement));
    }

    @Test()
    @DisplayName("Test method: clear()")
    public void testClearMethod() {
        List actualList = generateTestList(stringsList);
        actualList.clear();
        assertEquals(0, actualList.size(), "List 'stringsList' not cleared");
        assertTrue(actualList.isEmpty(), "List 'stringsList' not cleared");
    }

    @Test()
    @DisplayName("Test method: isEmpty()")
    public void testIsEmptyMethod() {
        List actualList = generateTestList(stringsList);
        assertFalse(actualList.isEmpty(), "Method isEmpty should be: false");
        actualList.clear();
        assertTrue(actualList.isEmpty(), "Method isEmpty should be: true");
    }

    @Test()
    @DisplayName("Test method: contains(Object object)")
    public void testContainsMethod() {
        List actualList = generateTestList(stringsList);
        Object expectedElement = "A";
        assertTrue(actualList.contains(expectedElement),
                String.format("List 'stringsList' not contains element: '%s', expected: contains", expectedElement));
        expectedElement = "TEST";
        assertFalse(actualList.contains(expectedElement),
                String.format("List 'stringsList' contains element: '%s', expected: not contains", expectedElement));
    }

    @Test()
    @DisplayName("Test method: Collections.addAll(Collection<? super T> c, T... elements)")
    public void testCollectionsAddAllMethod() {
        List<Object> actualList = generateTestList(stringsList);
        String[] newArray = {"test1", "test2", "test3"};
        Collections.addAll(actualList, newArray);
        assertTrue(actualList.contains(newArray[0]), "List 'stringsList' does not contain an element under index 0 from list 'newList'");
        assertTrue(actualList.contains(newArray[1]), "List 'stringsList' does not contain an element under index 1 from list 'newList'");
        assertTrue(actualList.contains(newArray[2]), "List 'stringsList' does not contain an element under index 2 from list 'newList'");
    }

    @Test()
    @DisplayName("Test method: Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)")
    public void testCollectionsCopyMethod() {
        List<Object> actualList = generateTestList(stringsList);
        List<String> newList = Arrays.asList("test1", "test2", "test3");
        Collections.copy(actualList, newList);
        assertTrue(actualList.contains(newList.get(0)), "List 'stringsList' does not contain an element under index 0 from list 'newList'");
        assertTrue(actualList.contains(newList.get(1)), "List 'stringsList' does not contain an element under index 1 from list 'newList'");
        assertTrue(actualList.contains(newList.get(2)), "List 'stringsList' does not contain an element under index 2 from list 'newList'");
    }

    @Test()
    @DisplayName("Test method: Collections.static <T> void sort(List<T> list, Comparator<? super T> c)")
    public void testCollectionsSortMethod() {
        List actualStringsList = generateTestList(stringsList);
        List actualIntegersList = generateTestList(integersList);
        List<String> expectedStringsList = Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Z");
        List<Integer> expectedIntegersList = Arrays.asList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25);
        Collections.sort(actualStringsList);
        Collections.sort(actualIntegersList);
        assertArrayEquals(expectedStringsList.toArray(), actualStringsList.toArray(), "List 'actualStringsList' not equals 'expectedStringsList', sort is failed");
        assertArrayEquals(expectedIntegersList.toArray(), actualIntegersList.toArray(), "List 'actualIntegersList' not equals 'expectedIntegersList', sort is failed");
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
