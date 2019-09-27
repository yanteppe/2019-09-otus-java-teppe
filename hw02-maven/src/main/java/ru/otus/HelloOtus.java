package ru.otus;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.List;

/**
 * Home work №2. Maven.
 * Guava demo class
 */
public class HelloOtus {
    public static void main(String args[]) {
        ArrayList<Integer> numbers = Lists.newArrayList(2, 6, 7, 1, 0, 3, 10, 4, 8, 5, 9);
        System.out.println("List: " + numbers);
        System.out.println("Sorted list: " + new HelloOtus().getSortedNumbers(numbers));
    }

    /**
     * Sort integers through Guava
     *
     * @param numbers list of numbers
     * @return sorted list of numbers
     */
    private List getSortedNumbers(List numbers) {
        numbers.sort(Ordering.natural());
        return numbers;
    }
}
