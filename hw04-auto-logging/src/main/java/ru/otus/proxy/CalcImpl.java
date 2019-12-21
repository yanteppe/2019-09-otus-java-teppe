package ru.otus.proxy;

public class CalcImpl implements Calc {
    @Log
    @Override
    public void sumХ(int x, int y) {
        System.out.println(String.format("Result: %s + %s = " + (x + y), x, y));
    }

    @Log
    @Override
    public void sumY(int x, int y, int z) {
        System.out.println(String.format("Result: %s + %s + %s = " + (x + y + z), x, y, z));
    }
}