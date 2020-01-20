package ru.otus.testframework;

import ru.otus.testframework.annotation.After;
import ru.otus.testframework.annotation.Before;
import ru.otus.testframework.annotation.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class TestClass {
    private String classnamePrefix;
    private List<Method> beforeMethods;
    private List<Method> testMethods;
    private List<Method> afterMethods;

    TestClass(String classNamePrefix) {
        this.classnamePrefix = classNamePrefix;
    }

    /**
     * Run test framework
     */
    void execute() {
        List<Class> annotatedClasses = findAnnotatedClassesWithAnnotatedMethods(getClassesList(getClassPath()));
        for (int i = 0; i < annotatedClasses.size(); i++) {
            collectAnnotatedMethods(annotatedClasses.get(i));
            for (Method method : testMethods) {
                try {
                    callMethod(method, annotatedClasses.get(i).getDeclaredConstructor().newInstance());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * Search the class list of the class that contains the methods with the @Test annotation
     *
     * @param classes list of classes
     * @return list of classes with annotated methods
     */
    private List<Class> findAnnotatedClassesWithAnnotatedMethods(List<String> classes) {
        List<Class> annotatedClasses = new ArrayList<>();
        for (String className : classes) {
            Class clazz = null;
            try {
                clazz = Class.forName(className.replace(".class", ""));
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            }
            Method[] methods = Objects.requireNonNull(clazz).getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Test.class)) {
                    annotatedClasses.add(clazz);
                    break;
                }
            }
            if (Objects.requireNonNull(clazz).isAnnotationPresent(Test.class)) {
                annotatedClasses.add(clazz);
            }
        }
        return annotatedClasses;
    }

    /**
     * Get the path to the package with the framework classes
     *
     * @return path to the package with the framework classes
     */
    private String getClassPath() {
        File currentClass = null;
        try {
            currentClass = new File(URLDecoder.decode(FrameworkDemo.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath(), "UTF-8"));
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
        String path = currentClass + "." + classnamePrefix;
        return path.replace(".", FileSystems.getDefault().getSeparator());
    }

    /**
     * Get a list of classes by package path
     *
     * @param path class package path
     * @return list names of classes in package
     */
    private List<String> getClassesList(String path) {
        // TODO: добавить обратотку ошики если указан не верный путь к пакету фреймворка
        File[] files = Objects.requireNonNull(new File(path).listFiles());
        List<String> classes = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                classes.add(classnamePrefix + "." + file.getName());
            }
        }
        return classes;
    }

    /**
     * Collect annotated methods in a class
     *
     * @param testingClass class with annotated methods
     */
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

    /**
     * Call annotated method of object instance
     *
     * @param invokeMethod   method called
     * @param instanceObject instance of object
     */
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
}
