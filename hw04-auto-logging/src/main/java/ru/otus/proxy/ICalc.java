package ru.otus.proxy;

public interface ICalc {
    @Log
    void sum(int x, int y);
}