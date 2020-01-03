package ru.otus.hw;

public class FrameworkDemo {

    public static void main(String[] args) {
        if (args.length == 0) {
            String classPath = "ru.otus.hw.TestMethods";
            args = new String[]{classPath};
        }
        TestClass testClass = new TestClass(args[0]);
        testClass.execute();
    }
}