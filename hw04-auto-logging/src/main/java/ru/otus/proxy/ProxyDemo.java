package ru.otus.proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        Calc calc = new IoC().createClass("sumY");
        calc.sumY(111,222,333);
    }
}