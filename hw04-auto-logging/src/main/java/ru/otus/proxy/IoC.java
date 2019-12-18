package ru.otus.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class IoC {

    Calc createClass(String methodName) {
        InvocationHandler handler = null;
        try {
            handler = new Handler(new CalcImpl(), methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return (Calc) Proxy.newProxyInstance(IoC.class.getClassLoader(), new Class<?>[]{Calc.class}, handler);
    }


    public class Handler implements InvocationHandler {
        private CalcImpl calc;
        boolean annotationFlag = false;

        Handler(Calc calc, String methodName) throws NoSuchMethodException {
            this.calc = (CalcImpl) calc;
            if (calc.getClass().getMethod(String.valueOf(methodName), int.class, int.class).isAnnotationPresent(Log.class)) {
                annotationFlag = true;
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (annotationFlag) {
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