package ru.otus.atm_department.atm;

public class FrameworkDemo {

    /**
     * Before starting, in arguments specify the path of the root package of the test framework<br>
     * ru.otus.hw
     *
     * @param args arguments from startup options (ru.otus.hw)
     */
    public static void main(String[] args) {
        TestClass testClass = new TestClass(args[0]);
        testClass.execute();
    }
}


