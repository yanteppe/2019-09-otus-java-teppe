package ru.otus.hw;

public class FrameworkDemo {
    private static String classPath = "ru.otus.hw.TestMethods";

    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[]{classPath};
        }
        TestClass testClass = new TestClass(args[0]);
        testClass.execute();
    }
}