package ru.otus.proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        ICalc calc = IoC.createClass();
        calc.sum(1, 2);
    }
}