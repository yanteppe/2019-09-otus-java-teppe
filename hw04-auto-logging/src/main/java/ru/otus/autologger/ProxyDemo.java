package ru.otus.autologger;

public class ProxyDemo {
    public static void main(String[] args) {
        Calc calc = new IoC().createClass();
        calc.sum(1,2);
        calc.sumX(1,2, 3);
        calc.subtraction(10, 5);
        calc.multiplication(2, 2);
        calc.division(4, 2);
    }
}