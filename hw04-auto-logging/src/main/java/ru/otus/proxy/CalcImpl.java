package ru.otus.proxy;

public class CalcImpl implements Calc {
    @Log
    @Override
    public void sum(int x, int y) {
        System.out.println(String.format("Result: %s + %s = " + (x + y), x, y));
    }
}