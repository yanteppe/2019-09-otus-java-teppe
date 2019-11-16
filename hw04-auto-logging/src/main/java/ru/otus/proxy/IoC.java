package ru.otus.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;

class IoC {

    static ICalc createClass() {
        Handler handler = null;
        try {
            handler = new Handler(new Calc());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return (ICalc) Proxy.newProxyInstance(ICalc.class.getClassLoader(),
                new Class[]{ICalc.class}, Objects.requireNonNull(handler));
    }

    static class Handler implements InvocationHandler {
        private Calc calc;
        boolean annotationFlag = false;

        Handler(Calc calc) throws NoSuchMethodException {
            this.calc = calc;
            if (calc.getClass().getMethod("sum", int.class, int.class).isAnnotationPresent(Log.class)) {
                annotationFlag = true;
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (annotationFlag){
                printLog(calc.getClass().getMethod("sum", int.class, int.class), args);
            }
            method.invoke(calc, args);
            return this;
        }

        void printLog(Method method, Object[] args) {
            System.out.println("Executed method: " + method.getName() + ", params: " + Arrays.toString(args));
        }
    }
}