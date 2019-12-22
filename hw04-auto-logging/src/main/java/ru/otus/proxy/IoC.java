package ru.otus.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class IoC {

    Calc createClass() {
        InvocationHandler handler = null;
        try {
            handler = new Handler(new CalcImpl());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return (Calc) Proxy.newProxyInstance(IoC.class.getClassLoader(), new Class<?>[]{Calc.class}, handler);
    }

    public class Handler implements InvocationHandler {
        private CalcImpl calc;
        List<Method> annotatedMethods = new ArrayList<>();

        Handler(Calc calc) throws NoSuchMethodException {
            this.calc = (CalcImpl) calc;
            for (Method method : calc.getClass().getMethods()) {
                if (calc.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Log.class)) {
                    annotatedMethods.add(method);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (Method annotatedMethod : annotatedMethods) {
                if (annotatedMethod.getName().equals(method.getName())) {
                    printLog(calc.getClass().getMethod(method.getName(), method.getParameterTypes()), args);
                }
            }
            return method.invoke(calc, args);
        }

        void printLog(Method method, Object[] args) {
            System.out.println("Executed method: " + method.getName() + ", params: " + Arrays.toString(args));
        }
    }
}