package ru.otus.autologger;

public class CalcImpl implements Calc {
    @Log
    @Override
    public void sum(int x, int y) {
        System.out.println(String.format("Result: %s + %s = " + (x + y), x, y) + "\n");
    }

    @Log
    @Override
    public void sumX(int x, int y, int z) {
        System.out.println(String.format("Result: %s + %s + %s = " + (x + y + z), x, y, z) + "\n");
    }

    @Log
    @Override
    public void subtraction(int x, int y) {
        System.out.println(String.format("Result: %s - %s = " + (x - y), x, y) + "\n");
    }

    @Log
    @Override
    public void multiplication(int x, int y) {
        System.out.println(String.format("Result: %s * %s = " + (x * y), x, y) + "\n");
    }

    @Log
    @Override
    public void division(int x, int y) {
        System.out.println(String.format("Result: %s / %s = " + (x / y), x, y) + "\n");
    }
}