package ru.otus.proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        Calc calc = new IoC().createClass("sum");
        calc.sum(1, 2);
    }
}