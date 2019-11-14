package ru.otus.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class IoC {

    static ICalc createClass() {
        Handler handler = new Handler(new Calc());
        return (ICalc) Proxy.newProxyInstance(ICalc.class.getClassLoader(),
                new Class[]{ICalc.class},
                handler);
    }

    static class Handler implements InvocationHandler {
        private ICalc iCalc;

        Handler(ICalc iCalc) {
            this.iCalc = iCalc;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Log.class)) {
                printLog(method, args);
            }
            method.invoke(iCalc, args);
            return null;
        }

        void printLog(Method method, Object[] args) {
            System.out.println("Executed method: " + method.getName() + ", params: " + Arrays.toString(args));
        }
    }
}