package ru.otus;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.List;

/**
 * Home work №1. Maven.
 * Guava demo class
 */
public class HelloOtus {

    /**
     * Sort integers through Guava
     *
     * @param numbers list of numbers
     * @return sorted list of numbers
     */
    protected List getSortedNumbers(List numbers) {
        numbers.sort(Ordering.natural());
        return numbers;
    }
}
