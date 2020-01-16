package ru.otus.atm_department.proxy;

public interface Calc {

    void sum(int x, int y);

    void sumX(int x, int y, int z);

    void subtraction (int x, int y);

    void multiplication(int x, int y);

    void division(int x, int y);
}