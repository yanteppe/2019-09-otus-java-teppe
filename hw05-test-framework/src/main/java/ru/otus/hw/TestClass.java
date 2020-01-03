package ru.otus.hw;

import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    private String className;
    private int testCounter = 0;
    private int testPassed = 0;
    private List<Method> beforeMethods;
    private List<Method> testMethods;
    private List<Method> afterMethods;

    public TestClass(String className) {
        this.className = className;
    }

    void execute() {
        try {
            Class testingClass = Class.forName(className);
            collectAnnotatedMethods(testingClass);
            for (Method method : testMethods) {
                try {
                    callMethod(method, testingClass.getDeclaredConstructor().newInstance());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private void collectAnnotatedMethods(Class testingClass) {
        beforeMethods = new ArrayList<>();
        testMethods = new ArrayList<>();
        afterMethods = new ArrayList<>();
        for (Method method : testingClass.getDeclaredMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation instanceof Before) {
                    beforeMethods.add(method);
                    break;
                } else if (annotation instanceof Test) {
                    testMethods.add(method);
                    break;
                } else if (annotation instanceof After) {
                    afterMethods.add(method);
                    break;
                }
            }
        }
    }

    private void callMethod(Method invokeMethod, Object instanceObject) {
        try {
            try {
                for (Method method : beforeMethods) {
                    method.invoke(instanceObject);
                }
                invokeMethod.invoke(instanceObject);
            } finally {
                for (Method method : afterMethods) {
                    method.invoke(instanceObject);
                }
            }
            System.out.printf("OK: '%s'\n", invokeMethod.getName());
        } catch (InvocationTargetException | IllegalAccessException exception) {
            System.out.printf("FAILED: '%s'\n", invokeMethod.getName());
        }
    }

    void getTestsData() {
    }
}
