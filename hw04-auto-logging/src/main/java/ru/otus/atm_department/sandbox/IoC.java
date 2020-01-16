package ru.otus.atm_department.sandbox;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class IoC {


//    static Calc createClass() {
//        InvocationHandler handler = new DemoInvocationHandler(new CalcImpl());
//        return (Calc) MyProxy.newProxyInstance(IoC.class.getClassLoader(),
//                new Class<?>[] {Calc.class}, handler);
//    }
//
//    static class DemoInvocationHandler implements InvocationHandler {
//        private final Calc myClass;
//
//        DemoInvocationHandler(Calc myClass) {
//            this.myClass = myClass;
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            System.out.println("invoking method:" + method);
//            return method.invoke(myClass, args);
//        }
//
//        @Override
//        public String toString() {
//            return "DemoInvocationHandler{" +
//                    "myClass=" + myClass +
//                    '}';
//        }
//    }

}
