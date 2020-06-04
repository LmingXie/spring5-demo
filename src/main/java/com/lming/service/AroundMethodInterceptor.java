package com.lming.service;

import java.lang.reflect.InvocationTargetException;

public class AroundMethodInterceptor implements MethodInterceptor {
    public Object invoke(MethodInvocation methodInvocation) throws InvocationTargetException, IllegalAccessException {
        System.out.println("环绕通知之前执行....");
        Object process = methodInvocation.process();
        System.out.println("环绕通知之后执行....");
        return process;
    }
}
