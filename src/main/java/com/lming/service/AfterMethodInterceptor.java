package com.lming.service;

import java.lang.reflect.InvocationTargetException;

public class AfterMethodInterceptor implements MethodInterceptor {
    public Object invoke(MethodInvocation methodInvocation) throws InvocationTargetException, IllegalAccessException {
        Object process = methodInvocation.process();
        System.out.println("后置通知");
        return process;
    }
}
