package ru.otus.testframework;

public class FrameworkDemo {

    /**
     * Before starting, in arguments specify the path of the root package of the test framework<br>
     * ru.otus.testframework
     *
     * @param args arguments from startup options (ru.otus.testframework)
     */
    public static void main(String[] args) {
        TestClass testClass = new TestClass(args[0]);
        testClass.execute();
    }
}


