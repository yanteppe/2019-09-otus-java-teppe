package ru.otus.maven;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {
        ArrayList<Integer> numbers = Lists.newArrayList(2, 6, 7, 1, 0, 3, 10, 4, 8, 5, 9);
        System.out.println("List: " + numbers);
        System.out.println("Sorted list: " + new HelloOtus().getSortedNumbers(numbers));
    }
}
