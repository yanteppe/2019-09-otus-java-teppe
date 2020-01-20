package ru.otus.sandbox;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyDemo {
    public static void main(String[] args) {
        Original original = new Original();
        Handler handler = new Handler(original);
        PrintInterface printInterface = (PrintInterface) Proxy.newProxyInstance(PrintInterface.class.getClassLoader(),
                new Class[]{PrintInterface.class},
                handler);
        printInterface.printMethod("Hallo");
    }

    interface PrintInterface {
        void printMethod(String s);
    }

    static class Original implements PrintInterface {
        public void printMethod(String s) {
            System.out.println(s);
        }
    }

    static class Handler implements InvocationHandler {
        private final PrintInterface original;

        Handler(PrintInterface original) {
            this.original = original;
        }

        public Object invoke(Object proxy, Method method, Object[] args)
                throws IllegalAccessException, IllegalArgumentException,
                InvocationTargetException {
            System.out.println("BEFORE");
            method.invoke(original, args);
            System.out.println("AFTER");
            return null;
        }
    }
}
